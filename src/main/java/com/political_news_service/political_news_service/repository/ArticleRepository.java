package com.political_news_service.political_news_service.repository;

import com.political_news_service.political_news_service.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    boolean existsByUrl(String url);
}
