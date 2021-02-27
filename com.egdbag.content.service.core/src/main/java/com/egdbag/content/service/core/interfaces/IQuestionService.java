package com.egdbag.content.service.core.interfaces;

import com.egdbag.content.service.core.model.survey.Question;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IQuestionService {
    Mono<Question> createQuestion(Question question, Integer componentId);

    Mono<Question> findById(Integer questionId);

    Mono<Question> updateQuestion(Integer questionId, Question question);

    Mono<Question> deleteQuestion(Integer questionId);

    Flux<Question> getQuestionsByComponentId(Integer componentId);
}
