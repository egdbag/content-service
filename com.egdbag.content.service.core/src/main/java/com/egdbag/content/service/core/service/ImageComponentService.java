package com.egdbag.content.service.core.service;

import com.egdbag.content.service.core.interfaces.IImageComponentService;
import com.egdbag.content.service.core.interfaces.IImageStorage;
import com.egdbag.content.service.core.model.ImageComponent;
import com.egdbag.content.service.core.model.ModelMapper;
import com.egdbag.content.service.core.storage.repository.IImageComponentRepository;
import com.egdbag.content.service.core.storage.schema.ImageComponentSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URL;

@Service
public class ImageComponentService implements IImageComponentService {
    @Autowired
    private IImageComponentRepository imageComponentRepository;
    @Autowired
    private IImageStorage imageStorage;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Mono<URL> getImage(String fileName)
    {
        return Mono.just(imageStorage.getImage(fileName));
    }

    @Override
    public Mono<ImageComponent> createComponent(ImageComponent component, Integer articleId) {
        ImageComponentSchema componentSchema = modelMapper.toSchema(component, articleId);
        return imageComponentRepository.save(componentSchema)
                .flatMap(c -> imageStorage.downloadImage(component.getUri(), c.getId())
                        .map(uri -> {
                            componentSchema.setUri("/components/image/files/" + uri);
                            return componentSchema;
                        }))
                .flatMap(dbComponent -> imageComponentRepository.save(dbComponent))
                .map(modelMapper::toDto);
    }

    @Override
    public Mono<ImageComponent> findById(Integer componentId) {
        return imageComponentRepository.findById(componentId)
                .map(modelMapper::toDto);
    }

    @Override
    public Mono<ImageComponent> deleteComponent(Integer componentId) {
        Mono<ImageComponent> deletedComponent = imageComponentRepository.findById(componentId)
                .flatMap(existingComponent -> imageComponentRepository.delete(existingComponent)
                        .then(Mono.just(modelMapper.toDto(existingComponent))));
        deletedComponent.subscribe(c -> imageStorage.deleteImage(c.getUri().toString()));
        return deletedComponent;
    }

    @Override
    public Flux<ImageComponent> getComponentsByArticleId(Integer articleId) {
        return imageComponentRepository.findByArticleId(articleId)
                .map(modelMapper::toDto);
    }
}
