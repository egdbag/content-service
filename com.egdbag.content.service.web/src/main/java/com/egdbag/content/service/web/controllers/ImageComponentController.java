package com.egdbag.content.service.web.controllers;

import com.egdbag.content.service.core.interfaces.IImageComponentService;
import com.egdbag.content.service.core.model.ImageComponent;
import com.egdbag.content.service.core.model.TextComponent;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/components/image")
public class ImageComponentController {
    @Autowired
    private IImageComponentService imageComponentService;

    @GetMapping(value = "/files/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    Mono<Resource> getFile(@PathVariable String fileName)
    {
        return imageComponentService.getImage(fileName)
                .map(file -> new FileUrlResource(file));
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200")
    Mono<ResponseEntity<ImageComponent>> getById(@PathVariable("id") Integer id) {
        return imageComponentService.findById(id)
                .map(u -> ResponseEntity.ok(u))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    Mono<ResponseEntity<Void>> deleteById(@PathVariable Integer id) {
        return imageComponentService.deleteComponent(id)
                .map( r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
