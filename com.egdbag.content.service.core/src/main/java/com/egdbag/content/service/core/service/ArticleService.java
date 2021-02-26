package com.egdbag.content.service.core.service;

import com.egdbag.content.service.core.interfaces.IArticleService;
import com.egdbag.content.service.core.model.Article;
import com.egdbag.content.service.core.storage.repository.IArticleRepository;
import com.egdbag.content.service.core.storage.schema.ArticleSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
public class ArticleService implements IArticleService {
    @Autowired
    private IArticleRepository articleRepository;
    @Autowired ModelMapper modelMapper;

    @Override
    public Mono<Article> createArticle(Article article) {
        ArticleSchema articleSchema = modelMapper.toSchema(article);
        articleSchema.setTs(Instant.now());
        return articleRepository.save(articleSchema).map(modelMapper::toDto);
    }

    @Override
    public Flux<Article> getAllArticles() {
        return articleRepository.findAll().map(modelMapper::toDto);
    }

    @Override
    public Mono<Article> findById(Integer articleId) {
        return articleRepository.findById(articleId).map(modelMapper::toDto);
    }

    @Override
    public Mono<Article> updateArticle(Integer articleId, Article article) {
        return articleRepository.findById(articleId)
                .flatMap(dbArticle -> {
                    dbArticle.setTitle(article.getTitle());
                    return articleRepository.save(dbArticle);
                })
                .map(modelMapper::toDto);
    }

    public Mono<Article> deleteArticle(Integer articleId) {
        return articleRepository.findById(articleId)
                .flatMap(existingArticle -> articleRepository.delete(existingArticle)
                        .then(Mono.just(modelMapper.toDto(existingArticle))));
    }

}
