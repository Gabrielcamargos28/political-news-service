package com.political_news_service.political_news_service.service;

import com.political_news_service.political_news_service.domain.Article;
import com.political_news_service.political_news_service.domain.PoliticalSource;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ScraperService {

    public List<Article> fetchLatestNews(PoliticalSource source) {
        List<Article> articles = new ArrayList<>();
        try {
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(new URI(source.getRssUrl()).toURL()));

            for (SyndEntry entry : feed.getEntries()) {
                String url = entry.getLink();
                String title = entry.getTitle();
                
                String cleanText = extractContent(url);
                
                Article article = Article.builder()
                        .url(url)
                        .title(title)
                        .sourceName(source.getName())
                        .politicalBias(source.getBias())
                        .originalText(cleanText)
                        .publishedAt(entry.getPublishedDate().toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime())
                        .build();
                
                articles.add(article);
            }
        } catch (Exception e) {
            log.error("Error fetching news from source: {}", source.getName(), e);
        }
        return articles;
    }

    private String extractContent(String url) {
        try {
            log.info("Acessando link para extrair notícia completa: {}", url);

            // Jsoup conecta na URL da notícia com timeout maior e User-Agent de navegador
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                    .timeout(15000)
                    .get();

            // 1. LIMPEZA PROFUNDA: Removemos todo o "lixo" que não é o texto da notícia
            doc.select("script, style, iframe, nav, footer, header, aside").remove();
            doc.select(".h-newsletter, #form, .div_internas_teads").remove(); // Específico da CartaCapital/Teads
            doc.select("[id^=google_ads_iframe], .ad-container, .propaganda").remove(); // Remove divs de anúncios genéricos

            // 2. BUSCA DO CONTEÚDO:
            // O '.entry-content' pega bem na CartaCapital.
            // Os outros são para G1 e etc.
            String text = doc.select("article, .mc-body, .materia-conteudo, .entry-content, .noticia-texto, #materia-corpo").text();

            // 3. Fallback (Plano B)
            if (text == null || text.trim().isEmpty()) {
                text = doc.body().text();
            }

            return text;

        } catch (Exception e) {
            log.warn("Falha ao extrair conteúdo da URL: {}. Motivo: {}", url, e.getMessage());
            return null;
        }
    }
}
