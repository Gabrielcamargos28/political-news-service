package com.political_news_service.political_news_service.scheduler;

import com.political_news_service.political_news_service.domain.Article;
import com.political_news_service.political_news_service.domain.PoliticalSource;
import com.political_news_service.political_news_service.repository.ArticleRepository;
import com.political_news_service.political_news_service.service.AiAnalyzerService;
import com.political_news_service.political_news_service.service.ScraperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class NewsScheduler {

    private final ScraperService scraperService;
    private final AiAnalyzerService aiAnalyzerService;
    private final ArticleRepository articleRepository;

    // Roda a cada 2 horas
    @Scheduled(cron = "0 0 */2 * * *")
    public void scheduledProcess() {
        processNews();
    }

    public void processNews() {
        log.info("Starting news collection and analysis routine...");

        for (PoliticalSource source : PoliticalSource.values()) {
            log.info("Fetching news from: {}", source.getName());
            List<Article> latestArticles = scraperService.fetchLatestNews(source);

            for (Article article : latestArticles) {
                if (!articleRepository.existsByUrl(article.getUrl())) {
                    log.info("Processing new article: {}", article.getTitle());
                    
                    String summary = aiAnalyzerService.summarize(article);
                    article.setGeminiSummary(summary);
                    
                    articleRepository.save(article);
                    log.info("Saved article: {}", article.getTitle());
                } else {
                    log.debug("Article already exists: {}", article.getUrl());
                }
            }
        }
        log.info("News routine finished.");
    }
}
