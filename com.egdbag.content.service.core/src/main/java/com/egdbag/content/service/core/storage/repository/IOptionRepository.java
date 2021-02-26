package com.egdbag.content.service.core.storage.repository;

import com.egdbag.content.service.core.storage.schema.survey.OptionSchema;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface IOptionRepository extends ReactiveCrudRepository<OptionSchema, Integer> {
    Mono<OptionSchema> findByQuestionId(Integer questionId);
}