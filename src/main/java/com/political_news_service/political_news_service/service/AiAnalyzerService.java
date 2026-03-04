package com.political_news_service.political_news_service.service;

import com.political_news_service.political_news_service.domain.Article;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AiAnalyzerService {


    public AiAnalyzerService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    private final ChatModel chatModel;

    public String summarize(Article article) {
        if (article.getOriginalText() == null || article.getOriginalText().isBlank()) {
            return "Texto original não disponível para resumo.";
        }

        String promptTemplate = """
            Você é um analista político sênior.
            Resuma a notícia a seguir em 3 bullet points objetivos.
            Considere que o texto foi publicado pelo portal %s, que possui um viés de %s.
            Extraia as principais entidades (políticos, partidos, instituições) mencionadas.
            
            Título: %s
            Texto: %s
            """;

        String formattedPrompt = String.format(
                promptTemplate,
                article.getSourceName(),
                article.getPoliticalBias(),
                article.getTitle(),
                truncate(article.getOriginalText(), 10000) // Limita tamanho do texto para evitar estouro de tokens
        );

        try {
            return chatModel.call(formattedPrompt);
        } catch (Exception e) {
            log.error("Erro ao chamar o Gemini para o artigo: {}", article.getUrl(), e);
            return "Erro ao gerar resumo via IA.";
        }
    }

    private String truncate(String text, int maxLength) {
        if (text.length() <= maxLength) return text;
        return text.substring(0, maxLength);
    }
}
