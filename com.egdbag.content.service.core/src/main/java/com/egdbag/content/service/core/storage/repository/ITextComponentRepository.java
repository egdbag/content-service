package com.egdbag.content.service.core.storage.repository;

import com.egdbag.content.service.core.storage.schema.TextComponentSchema;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ITextComponentRepository extends ReactiveCrudRepository<TextComponentSchema, Integer> {
    @Query("select * from text_components where article_id = $1")
    Flux<TextComponentSchema> findByArticleId(Integer articleId);
}