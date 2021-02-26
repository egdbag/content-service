package com.egdbag.content.service.core.storage.repository;

import com.egdbag.content.service.core.storage.schema.survey.SurveyComponentSchema;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ISurveyComponentRepository extends ReactiveCrudRepository<SurveyComponentSchema, Integer> {
    Mono<SurveyComponentSchema> findByArticleId(Integer articleId);
}
