package com.egdbag.content.service.core.interfaces;

import com.egdbag.content.service.core.model.survey.Option;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IOptionService {
    Mono<Option> createOption(Option option, Integer questionId);

    Mono<Option> findById(Integer optionId);

    Mono<Option> updateOption(Integer optionId, Option option);

    Mono<Option> deleteOption(Integer optionId);

    Flux<Option> getOptionsByQuestionId(Integer questionId);
}
