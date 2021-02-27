package com.egdbag.content.service.core.service;

import com.egdbag.content.service.core.interfaces.IArticleService;
import com.egdbag.content.service.core.interfaces.ISurveyComponentService;
import com.egdbag.content.service.core.interfaces.ITextComponentService;
import com.egdbag.content.service.core.model.Article;
import com.egdbag.content.service.core.model.Component;
import com.egdbag.content.service.core.model.ModelMapper;
import com.egdbag.content.service.core.storage.repository.IArticleRepository;
import com.egdbag.content.service.core.storage.schema.ArticleSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.ArrayList;

@Service
public class ArticleService implements IArticleService {
    @Autowired
    private IArticleRepository articleRepository;
    @Autowired
    private ITextComponentService textComponentService;
    @Autowired
    private ISurveyComponentService surveyComponentService;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Mono<Article> createArticle(Article article) {
        ArticleSchema articleSchema = modelMapper.toSchema(article);
        articleSchema.setTimestamps(getCurrentTimestamp());
        return articleRepository.save(articleSchema).map(modelMapper::toDto);
    }

    @Override
    public Flux<Article> getAllArticles() {
        return articleRepository.findAll().flatMap(this::convertToFullDto);
    }

    @Override
    public Mono<Article> findById(Integer articleId) {
        return articleRepository.findById(articleId).flatMap(this::convertToFullDto);
    }

    @Override
    public Mono<Article> updateArticle(Integer articleId, Article article) {
        return articleRepository.findById(articleId)
                .flatMap(dbArticle -> {
                    dbArticle.setTitle(article.getTitle());
                    dbArticle.setTimestamps(dbArticle.getTimestamps() + ',' + getCurrentTimestamp());
                    return articleRepository.save(dbArticle);
                })
                .map(modelMapper::toDto);
    }

    @Override
    public Mono<Article> deleteArticle(Integer articleId) {
        return articleRepository.findById(articleId)
                .flatMap(existingArticle -> articleRepository.delete(existingArticle)
                        .then(Mono.just(modelMapper.toDto(existingArticle))));
    }

    private Mono<Article> convertToFullDto(ArticleSchema article) {
        return Flux.fromIterable(new ArrayList<Component>())
                .concatWith(textComponentService.getComponentsByArticleId(article.getId()))
                .concatWith(surveyComponentService.getComponentsByArticleId(article.getId()))
                .collectList()
                .map(components -> modelMapper.toDto(article, new ArrayList<>(components)));
    }

    private String getCurrentTimestamp() {
        return Long.toString(Instant.now().getEpochSecond());
    }
}
