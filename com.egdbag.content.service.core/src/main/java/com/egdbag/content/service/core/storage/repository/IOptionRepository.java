package com.egdbag.content.service.core.storage.repository;

import com.egdbag.content.service.core.storage.schema.survey.OptionSchema;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface IOptionRepository extends ReactiveCrudRepository<OptionSchema, Integer> {
    @Query("select * from options where question_id = $1")
    Flux<OptionSchema> findByQuestionId(Integer questionId);
}