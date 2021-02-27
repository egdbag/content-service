package com.egdbag.content.service.core.service;

import com.egdbag.content.service.core.interfaces.IOptionService;
import com.egdbag.content.service.core.interfaces.IQuestionService;
import com.egdbag.content.service.core.model.ModelMapper;
import com.egdbag.content.service.core.model.survey.Question;
import com.egdbag.content.service.core.storage.repository.IQuestionRepository;
import com.egdbag.content.service.core.storage.schema.survey.QuestionSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Service
public class QuestionService implements IQuestionService {
    @Autowired
    private IQuestionRepository questionRepository;
    @Autowired
    private IOptionService optionService;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Mono<Question> createQuestion(Question question, Integer componentId) {
        QuestionSchema questionSchema = modelMapper.toSchema(question, componentId);
        return questionRepository.save(questionSchema)
                .map(modelMapper::toDto);
    }

    @Override
    public Mono<Question> findById(Integer questionId) {
        return questionRepository.findById(questionId)
                .flatMap(this::convertToFullDto);
    }

    @Override
    public Mono<Question> updateQuestion(Integer questionId, Question question) {
        return questionRepository.findById(questionId)
                .flatMap(dbQuestion -> {
                    dbQuestion.setType(question.getType());
                    dbQuestion.setText(question.getText());
                    return questionRepository.save(dbQuestion);
                })
                .map(modelMapper::toDto);
    }

    @Override
    public Mono<Question> deleteQuestion(Integer questionId) {
        return questionRepository.findById(questionId)
                .flatMap(existingQuestion -> questionRepository.delete(existingQuestion)
                        .then(Mono.just(modelMapper.toDto(existingQuestion))));
    }

    @Override
    public Flux<Question> getQuestionsByComponentId(Integer componentId) {
        return questionRepository.findBySurveyComponentId(componentId)
                .flatMap(this::convertToFullDto);
    }

    private Mono<Question> convertToFullDto(QuestionSchema question) {
        return optionService.getOptionsByQuestionId(question.getId())
                .collectList()
                .map(options -> modelMapper.toDto(question, new ArrayList<>(options)));
    }
}
