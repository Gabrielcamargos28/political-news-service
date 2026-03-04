package com.political_news_service.political_news_service.dto;

import com.political_news_service.political_news_service.domain.Article;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ArticleResponse {
    private Long id;
    private String title;
    private String url;
    private String sourceName;
    private String politicalBias;
    private String summary;
    private LocalDateTime publishedAt;

    public static ArticleResponse fromEntity(Article article) {
        return ArticleResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .url(article.getUrl())
                .sourceName(article.getSourceName())
                .politicalBias(article.getPoliticalBias())
                .summary(article.getGeminiSummary())
                .publishedAt(article.getPublishedAt())
                .build();
    }
}
