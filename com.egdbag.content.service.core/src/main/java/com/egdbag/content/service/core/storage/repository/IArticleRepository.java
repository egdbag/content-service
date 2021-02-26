package com.egdbag.content.service.core.storage.repository;

import com.egdbag.content.service.core.storage.schema.ArticleSchema;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IArticleRepository extends ReactiveCrudRepository<ArticleSchema, Integer> {
}
