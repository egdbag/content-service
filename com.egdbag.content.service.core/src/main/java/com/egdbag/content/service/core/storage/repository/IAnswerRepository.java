package com.egdbag.content.service.core.storage.repository;

import com.egdbag.content.service.core.storage.schema.survey.AnswerSchema;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface IAnswerRepository extends ReactiveCrudRepository<AnswerSchema, Integer> {
    @Query("select * from answers where question_id = $1")
    Flux<AnswerSchema> findByQuestionId(Integer question);
}