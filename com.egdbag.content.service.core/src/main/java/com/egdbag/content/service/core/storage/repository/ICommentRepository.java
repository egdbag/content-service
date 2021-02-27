package com.egdbag.content.service.core.storage.repository;

import com.egdbag.content.service.core.storage.schema.CommentSchema;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ICommentRepository extends ReactiveCrudRepository<CommentSchema, Integer> {
    @Query("select * from comments where text_component_id = $1")
    Flux<CommentSchema> findByTextComponentId(Integer textComponentId);
}