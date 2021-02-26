package com.egdbag.article.core.storage.repository;

import com.egdbag.article.core.storage.schema.survey.QuestionSchema;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface IQuestionRepository extends ReactiveCrudRepository<QuestionSchema, Integer> {
    Mono<QuestionSchema> findBySurveyComponentId(Integer surveyComponentId);
}