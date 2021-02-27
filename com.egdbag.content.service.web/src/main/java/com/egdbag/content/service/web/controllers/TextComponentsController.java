package com.egdbag.content.service.web.controllers;

import com.egdbag.content.service.core.interfaces.ICommentService;
import com.egdbag.content.service.core.interfaces.ITextComponentService;
import com.egdbag.content.service.core.model.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("components/text")
class TextComponentsController {
    @Autowired
    private ITextComponentService textComponentService;
    @Autowired
    private ICommentService commentService;

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

    @PostMapping(path = "{componentId}/comments", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "201")
    Mono<ResponseEntity<Object>> createComponent(@PathVariable Integer componentId, @RequestBody Comment comment) {
        Mono<TextComponent> component = textComponentService.findById(componentId);
        return component.flatMap(c -> commentService.createComment(comment, 1, componentId)
                .map(createdComment -> ResponseEntity.created(URI.create("/comments" + createdComment.getId())).build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/comments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200")
    Mono<ResponseEntity<Comment>> getCommentById(@PathVariable("id") Integer id) {
        return  commentService.findById(id)
                .map(u -> ResponseEntity.ok(u))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/comments/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200")
    Mono<ResponseEntity<Comment>> updateCommentById(@PathVariable Integer id, @RequestBody Comment comment) {
        return commentService.updateComment(id, comment)
                .map(updated -> ResponseEntity.ok(updated))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/comments/{id}")
    @ApiResponse(responseCode = "204")
    Mono<ResponseEntity<Void>> deleteCommentById(@PathVariable Integer id) {
        return commentService.deleteComment(id)
                .map( r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}