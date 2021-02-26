package com.egdbag.content.service.core.interfaces;

import com.egdbag.content.service.core.model.TextComponent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITextComponentService {
    Mono<TextComponent> createComponent(TextComponent component, Integer articleId);

    Mono<TextComponent> findById(Integer componentId);

    Mono<TextComponent> updateComponent(Integer componentId, TextComponent component);

    Mono<TextComponent> deleteComponent(Integer componentId);

    Flux<TextComponent> getComponentsByArticleId(Integer articleId);
}
