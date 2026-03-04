package com.political_news_service.political_news_service.service;

import com.political_news_service.political_news_service.dto.ArticleResponse;
import com.political_news_service.political_news_service.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ArticleQueryService {


    private final ArticleRepository articleRepository;

    public ArticleQueryService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Page<ArticleResponse> getAllArticles(Pageable pageable) {
        return articleRepository.findAll(pageable)
                .map(ArticleResponse::fromEntity);
    }
}
