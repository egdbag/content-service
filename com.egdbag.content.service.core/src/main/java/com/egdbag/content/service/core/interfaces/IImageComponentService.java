package com.egdbag.content.service.core.interfaces;

import com.egdbag.content.service.core.model.ImageComponent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URL;

public interface IImageComponentService {
    Mono<ImageComponent> createComponent(ImageComponent component, Integer articleId);

    Mono<ImageComponent> findById(Integer componentId);

    Mono<ImageComponent> deleteComponent(Integer componentId);

    Flux<ImageComponent> getComponentsByArticleId(Integer articleId);

    Mono<URL> getImage(String fileName);
}
