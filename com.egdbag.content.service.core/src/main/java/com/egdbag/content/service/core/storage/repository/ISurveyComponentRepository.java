package com.egdbag.content.service.core.storage.repository;

import com.egdbag.content.service.core.storage.schema.survey.SurveyComponentSchema;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ISurveyComponentRepository extends ReactiveCrudRepository<SurveyComponentSchema, Integer> {
    @Query("select * from survey_components where article_id = $1")
    Flux<SurveyComponentSchema> findByArticleId(Integer articleId);
}
