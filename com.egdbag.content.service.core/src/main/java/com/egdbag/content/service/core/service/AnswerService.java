package com.egdbag.content.service.core.service;

import com.egdbag.content.service.core.interfaces.IAnswerService;
import com.egdbag.content.service.core.model.ModelMapper;
import com.egdbag.content.service.core.model.survey.Answer;
import com.egdbag.content.service.core.storage.repository.IAnswerRepository;
import com.egdbag.content.service.core.storage.schema.survey.AnswerSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AnswerService implements IAnswerService {
    @Autowired
    private IAnswerRepository answerRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Mono<Answer> createAnswer(Answer answer, Integer userId, Integer questionId) {
        AnswerSchema answerSchema = modelMapper.toSchema(answer, userId, questionId);
        return answerRepository.save(answerSchema)
                .map(modelMapper::toDto);
    }

    @Override
    public Mono<Answer> findById(Integer answerId) {
        return answerRepository.findById(answerId)
                .map(modelMapper::toDto);
    }

    @Override
    public Mono<Answer> updateAnswer(Integer answerId, Answer answer) {
        return answerRepository.findById(answerId)
                .flatMap(dbAnswer -> {
                    dbAnswer.setOptionIds(modelMapper.concatenateOptions(answer.getOptionIds()));
                    return answerRepository.save(dbAnswer);
                })
                .map(modelMapper::toDto);
    }

    @Override
    public Mono<Answer> deleteAnswer(Integer answerId) {
        return answerRepository.findById(answerId)
                .flatMap(existingAnswer -> answerRepository.delete(existingAnswer)
                        .then(Mono.just(modelMapper.toDto(existingAnswer))));
    }

    @Override
    public Flux<Answer> getAnswersByQuestionId(Integer questionId) {
        return answerRepository.findByQuestionId(questionId)
                .map(modelMapper::toDto);
    }
}
