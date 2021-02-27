package com.egdbag.content.service.core.service;

import com.egdbag.content.service.core.interfaces.IQuestionService;
import com.egdbag.content.service.core.interfaces.ISurveyComponentService;
import com.egdbag.content.service.core.model.ModelMapper;
import com.egdbag.content.service.core.model.survey.SurveyComponent;
import com.egdbag.content.service.core.storage.repository.IQuestionRepository;
import com.egdbag.content.service.core.storage.repository.ISurveyComponentRepository;
import com.egdbag.content.service.core.storage.schema.survey.SurveyComponentSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Service
public class SurveyComponentService implements ISurveyComponentService {
    @Autowired
    private ISurveyComponentRepository surveyComponentRepository;
    @Autowired
    private IQuestionRepository questionRepository;
    @Autowired
    private IQuestionService questionService;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Mono<SurveyComponent> createComponent(SurveyComponent component, Integer articleId) {
        SurveyComponentSchema componentSchema = modelMapper.toSchema(component, articleId);
        return surveyComponentRepository.save(componentSchema)
                .map(modelMapper::toDto);
    }

    @Override
    public Mono<SurveyComponent> findById(Integer componentId) {
        return surveyComponentRepository.findById(componentId)
                .flatMap(this::convertToFullDto);
    }

    @Override
    public Mono<SurveyComponent> updateComponent(Integer componentId, SurveyComponent component) {
        return surveyComponentRepository.findById(componentId)
                .flatMap(dbComponent -> {
                    dbComponent.setText(component.getText());
                    return surveyComponentRepository.save(dbComponent);
                })
                .map(modelMapper::toDto);
    }

    @Override
    public Mono<SurveyComponent> deleteComponent(Integer componentId) {
        return surveyComponentRepository.findById(componentId)
                .flatMap(existingComponent -> surveyComponentRepository.delete(existingComponent)
                        .then(questionRepository.deleteBySurveyComponentId(componentId))
                        .then(Mono.just(modelMapper.toDto(existingComponent))));
    }

    @Override
    public Flux<SurveyComponent> getComponentsByArticleId(Integer articleId) {
        return surveyComponentRepository.findByArticleId(articleId)
                .flatMap(this::convertToFullDto);
    }

    private Mono<SurveyComponent> convertToFullDto(SurveyComponentSchema surveyComponent) {
        return questionService.getQuestionsByComponentId(surveyComponent.getId())
                .collectList()
                .map(questions -> modelMapper.toDto(surveyComponent, new ArrayList<>(questions)));
    }
}
