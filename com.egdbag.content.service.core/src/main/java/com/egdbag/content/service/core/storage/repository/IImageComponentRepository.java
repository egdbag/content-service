package com.egdbag.content.service.core.storage.repository;

import com.egdbag.content.service.core.storage.schema.ImageComponentSchema;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface IImageComponentRepository extends ReactiveCrudRepository<ImageComponentSchema, Integer> {
    @Query("select * from image_components where article_id = $1")
    Flux<ImageComponentSchema> findByArticleId(Integer articleId);
}