package com.egdbag.content.service.web.controllers;

import com.egdbag.content.service.core.interfaces.ISurveyComponentService;
import com.egdbag.content.service.core.interfaces.ITextComponentService;
import com.egdbag.content.service.core.model.Article;
import com.egdbag.content.service.core.model.Component;
import com.egdbag.content.service.core.interfaces.IArticleService;
import com.egdbag.content.service.core.model.TextComponent;
import com.egdbag.content.service.core.model.survey.SurveyComponent;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("articles")
class ArticlesController {
    @Autowired
    private IArticleService articleService;
    @Autowired
    private ITextComponentService textComponentService;
    @Autowired
    private ISurveyComponentService surveyComponentService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200")
    Flux<Article> getAll() {
        return articleService.getAllArticles();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200")
    Mono<ResponseEntity<Article>> getById(@PathVariable("id") Integer id) {
        return  articleService.findById(id)
                .map(u -> ResponseEntity.ok(u))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "201")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {
            @ExampleObject(value = "{\"title\":\"Test article\", \"hashTags\":[\"test1\", \"test2\"]}")
    }))
    Mono<ResponseEntity<URI>> create(@RequestBody Article article) {
        return articleService.createArticle(article)
                .map(a -> ResponseEntity.created(URI.create("/articles/" + a.getId()))
                        .build());
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200")
    Mono<ResponseEntity<Article>> updateById(@PathVariable Integer id, @RequestBody Article article) {
        return articleService.updateArticle(id, article)
                .map(updated -> ResponseEntity.ok(updated))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    Mono<ResponseEntity<Void>> deleteById(@PathVariable Integer id) {
        return articleService.deleteArticle(id)
                .map( r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/{id}/components", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "201")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {
            @ExampleObject(value = "{\"type\": \"TEXT\", \"text\": \"Lorem ipsum dolor sit amet\"}"),
            @ExampleObject(value = "{\"type\": \"SURVEY\", \"text\": \"Would you sign my petition?\"}")
    }))
    Mono<ResponseEntity<Object>> createComponent(@PathVariable Integer id, @RequestBody Component component) {
        Mono<Article> article = articleService.findById(id);
        return article.flatMap(a -> {
            if (component instanceof TextComponent) {
                return textComponentService.createComponent((TextComponent) component, id)
                        .map(c -> ResponseEntity.created(URI.create("/components/text/" + c.getId())).build());
            }
            else if (component instanceof SurveyComponent) {
                return surveyComponentService.createComponent((SurveyComponent) component, id)
                        .map(c -> ResponseEntity.created(URI.create("/components/survey/" + c.getId())).build());
            }
            else {
                return Mono.just(ResponseEntity.badRequest().build());
            }
        })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}