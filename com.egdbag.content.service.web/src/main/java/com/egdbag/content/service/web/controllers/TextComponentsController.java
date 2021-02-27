package com.egdbag.content.service.web.controllers;

import com.egdbag.content.service.core.interfaces.ITextComponentService;
import com.egdbag.content.service.core.model.TextComponent;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("components/text")
class TextComponentsController {
    @Autowired
    private ITextComponentService textComponentService;

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200")
    Mono<ResponseEntity<TextComponent>> getById(@PathVariable("id") Integer id) {
        return textComponentService.findById(id)
                .map(u -> ResponseEntity.ok(u))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {
            @ExampleObject(value = "{\"type\": \"TEXT\", \"text\": \"Lorem ipsum dolor sit amet\"}")
    }))
    @ApiResponse(responseCode = "200")
    Mono<ResponseEntity<TextComponent>> updateById(@PathVariable Integer id, @RequestBody TextComponent component) {
        return textComponentService.updateComponent(id, component)
                .map(updated -> ResponseEntity.ok(updated))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    Mono<ResponseEntity<Void>> deleteById(@PathVariable Integer id) {
        return textComponentService.deleteComponent(id)
                .map( r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}