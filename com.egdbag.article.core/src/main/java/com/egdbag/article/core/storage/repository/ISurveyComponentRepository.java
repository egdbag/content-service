package com.egdbag.article.core.storage.repository;

import com.egdbag.article.core.storage.schema.survey.SurveyComponentSchema;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ISurveyComponentRepository extends ReactiveCrudRepository<SurveyComponentSchema, Integer> {
    Mono<SurveyComponentSchema> findByArticleId(Integer articleId);
}
