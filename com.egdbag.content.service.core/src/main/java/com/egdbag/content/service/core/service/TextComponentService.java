package com.egdbag.content.service.core.service;

import com.egdbag.content.service.core.interfaces.ITextComponentService;
import com.egdbag.content.service.core.model.TextComponent;
import com.egdbag.content.service.core.storage.repository.ITextComponentRepository;
import com.egdbag.content.service.core.storage.schema.TextComponentSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TextComponentService implements ITextComponentService {
    @Autowired
    private ITextComponentRepository textComponentRepository;
    @Autowired ModelMapper modelMapper;

    @Override
    public Mono<TextComponent> createComponent(TextComponent component, Integer articleId) {
        TextComponentSchema componentSchema = modelMapper.toSchema(component, articleId);
        return textComponentRepository.save(componentSchema).map(modelMapper::toDto);
    }

    @Override
    public Mono<TextComponent> findById(Integer componentId) {
        return textComponentRepository.findById(componentId).map(modelMapper::toDto);
    }

    @Override
    public Mono<TextComponent> updateComponent(Integer componentId, TextComponent component) {
        return textComponentRepository.findById(componentId)
                .flatMap(dbComponent -> {
                    dbComponent.setText(component.getText());
                    return textComponentRepository.save(dbComponent);
                })
                .map(modelMapper::toDto);
    }

    @Override
    public Mono<TextComponent> deleteComponent(Integer componentId) {
        return textComponentRepository.findById(componentId)
                .flatMap(existingComponent -> textComponentRepository.delete(existingComponent)
                        .then(Mono.just(modelMapper.toDto(existingComponent))));
    }

    @Override
    public Flux<TextComponent> getComponentsByArticleId(Integer articleId) {
        return textComponentRepository.findByArticleId(articleId).map(modelMapper::toDto);
    }
}
