package com.political_news_service.political_news_service.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "articles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 2048)
    private String url;

    @Column(nullable = false, length = 512)
    private String title;

    @Column(name = "source_name", nullable = false, length = 100)
    private String sourceName;

    @Column(name = "political_bias", nullable = false, length = 50)
    private String politicalBias;

    @Column(name = "original_text", columnDefinition = "TEXT")
    private String originalText;

    @Column(name = "gemini_summary", columnDefinition = "TEXT")
    private String geminiSummary;

    @Column(name = "published_at", nullable = false)
    private LocalDateTime publishedAt;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}
