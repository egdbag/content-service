package com.egdbag.content.service.core.interfaces;

import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URL;

public interface IImageStorage {
    URL getImage(String fileName);

    Mono<String> downloadImage(URI uri, Integer id);

    void deleteImage(String fileName);
}