package com.egdbag.article.core.interfaces;

import com.egdbag.article.core.model.Article;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IArticleService {
    Mono<Article> createArticle(Article article);

    Flux<Article> getAllArticles();

    Mono<Article> findById(Integer articleId);

    Mono<Article> updateArticle(Integer articleId, Article article);

    Mono<Article> deleteArticle(Integer articleId);
}
