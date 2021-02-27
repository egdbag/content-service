package com.egdbag.content.service.core.service;

import com.egdbag.content.service.core.interfaces.IOptionService;
import com.egdbag.content.service.core.model.ModelMapper;
import com.egdbag.content.service.core.model.survey.Option;
import com.egdbag.content.service.core.storage.repository.IOptionRepository;
import com.egdbag.content.service.core.storage.schema.survey.OptionSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OptionService implements IOptionService {
    @Autowired
    private IOptionRepository optionRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Mono<Option> createOption(Option option, Integer questionId) {
        OptionSchema optionSchema = modelMapper.toSchema(option, questionId);
        return optionRepository.save(optionSchema)
                .map(modelMapper::toDto);
    }

    @Override
    public Mono<Option> findById(Integer optionId) {
        return optionRepository.findById(optionId)
                .map(modelMapper::toDto);
    }

    @Override
    public Mono<Option> updateOption(Integer optionId, Option option) {
        return optionRepository.findById(optionId)
                .flatMap(dbOption -> {
                    dbOption.setText(option.getText());
                    return optionRepository.save(dbOption);
                })
                .map(modelMapper::toDto);
    }

    @Override
    public Mono<Option> deleteOption(Integer optionId) {
        return optionRepository.findById(optionId)
                .flatMap(existingOption -> optionRepository.delete(existingOption)
                        .then(Mono.just(modelMapper.toDto(existingOption))));
    }

    @Override
    public Flux<Option> getOptionsByQuestionId(Integer questionId) {
        return optionRepository.findByQuestionId(questionId)
                .map(modelMapper::toDto);
    }
}
