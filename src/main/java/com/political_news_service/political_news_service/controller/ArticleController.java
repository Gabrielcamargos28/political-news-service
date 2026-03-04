package com.political_news_service.political_news_service.controller;

import com.political_news_service.political_news_service.dto.ArticleResponse;
import com.political_news_service.political_news_service.scheduler.NewsScheduler;
import com.political_news_service.political_news_service.service.ArticleQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleQueryService articleQueryService;
    private final NewsScheduler newsScheduler;

    @GetMapping
    public ResponseEntity<Page<ArticleResponse>> getArticles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "publishedAt"));
        Page<ArticleResponse> articles = articleQueryService.getAllArticles(pageRequest);
        
        return ResponseEntity.ok(articles);
    }

    @PostMapping("/run-process")
    public ResponseEntity<Map<String, String>> triggerProcess() {
        newsScheduler.processNews();
        return ResponseEntity.ok(Map.of("message", "Processo de coleta e análise iniciado manualmente."));
    }
}
