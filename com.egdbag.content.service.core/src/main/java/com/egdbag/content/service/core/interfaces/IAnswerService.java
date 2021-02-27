package com.egdbag.content.service.core.interfaces;

import com.egdbag.content.service.core.model.survey.Answer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAnswerService {
    Mono<Answer> createAnswer(Answer answer, Integer userId, Integer questionId);

    Mono<Answer> findById(Integer answerId);

    Mono<Answer> updateAnswer(Integer answerId, Answer answer);

    Mono<Answer> deleteAnswer(Integer answerId);

    Flux<Answer> getAnswersByQuestionId(Integer questionId);
}
