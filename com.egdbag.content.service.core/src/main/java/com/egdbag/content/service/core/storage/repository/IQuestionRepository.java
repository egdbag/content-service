package com.egdbag.content.service.core.storage.repository;

import com.egdbag.content.service.core.storage.schema.survey.QuestionSchema;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IQuestionRepository extends ReactiveCrudRepository<QuestionSchema, Integer> {
    @Query("select * from questions where survey_component_id = $1")
    Flux<QuestionSchema> findBySurveyComponentId(Integer surveyComponentId);

    @Query("delete * from questions where survey_component_id = $1")
    Mono<Void> deleteBySurveyComponentId(Integer surveyComponentId);
}