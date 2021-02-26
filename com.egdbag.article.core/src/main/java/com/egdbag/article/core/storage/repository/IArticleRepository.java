package com.egdbag.article.core.storage.repository;

import com.egdbag.article.core.storage.schema.ArticleSchema;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IArticleRepository extends ReactiveCrudRepository<ArticleSchema, Integer> {
}
