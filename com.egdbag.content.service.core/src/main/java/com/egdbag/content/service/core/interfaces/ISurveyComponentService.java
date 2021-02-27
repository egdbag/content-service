package com.egdbag.content.service.core.interfaces;

import com.egdbag.content.service.core.model.survey.SurveyComponent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ISurveyComponentService {
    Mono<SurveyComponent> createComponent(SurveyComponent component, Integer articleId);

    Mono<SurveyComponent> findById(Integer componentId);

    Mono<SurveyComponent> updateComponent(Integer componentId, SurveyComponent component);

    Mono<SurveyComponent> deleteComponent(Integer componentId);

    Flux<SurveyComponent> getComponentsByArticleId(Integer articleId);
}
