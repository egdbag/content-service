package com.egdbag.content.service.web.controllers;

import com.egdbag.content.service.core.interfaces.IImageComponentService;
import com.egdbag.content.service.core.interfaces.ISurveyComponentService;
import com.egdbag.content.service.core.interfaces.ITextComponentService;
import com.egdbag.content.service.core.model.Article;
import com.egdbag.content.service.core.model.Component;
import com.egdbag.content.service.core.interfaces.IArticleService;
import com.egdbag.content.service.core.model.ImageComponent;
import com.egdbag.content.service.core.model.TextComponent;
import com.egdbag.content.service.core.model.survey.SurveyComponent;
import com.egdbag.content.service.dto.IdDto;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("articles")
class ArticlesController {
    @Autowired
    private IArticleService articleService;
    @Autowired
    private ITextComponentService textComponentService;
    @Autowired
    private IImageComponentService imageComponentService;
    @Autowired
    private ISurveyComponentService surveyComponentService;

    @Autowired
    private MeterRegistry meterRegistry;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200")
    @ResponseBody Flux<Article> getAll() {
        return articleService.getAllArticles();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200")
    @ResponseBody Mono<ResponseEntity<Article>> getById(@PathVariable("id") Integer id) {
        return  articleService.findById(id)
                .map(u -> ResponseEntity.ok(u))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/html/{id}", produces = MediaType.TEXT_HTML_VALUE)
    @ApiResponse(responseCode = "200")
    Mono<Rendering> getAsHtml(@PathVariable("id") Integer id) {
        return articleService.findById(id)
                .doOnNext(article -> incrementViewCounter(id, article.getTitle()))
                .map(article ->
                    Rendering.view("index")
                            .modelAttribute("title", article.getTitle())
                            .modelAttribute("textComponents", article.getComponents().stream().filter(c -> c instanceof TextComponent).collect(toList()))
                            .modelAttribute("imageComponents", article.getComponents().stream().filter(c -> c instanceof ImageComponent).collect(toList()))
                            .modelAttribute("surveyComponents", article.getComponents().stream().filter(c -> c instanceof SurveyComponent).collect(toList()))
                            .build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {
            @ExampleObject(value = "{\"title\":\"Test article\", \"hashtags\":[\"test1\", \"test2\"]}")
    }))
    @ResponseBody Mono<IdDto> create(@RequestBody Article article) {
        return articleService.createArticle(article)
                .map(a -> new IdDto(a.getId()));
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200")
    @ResponseBody Mono<ResponseEntity<Article>> updateById(@PathVariable Integer id, @RequestBody Article article) {
        return articleService.updateArticle(id, article)
                .map(updated -> ResponseEntity.ok(updated))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    @ResponseBody Mono<ResponseEntity<Void>> deleteById(@PathVariable Integer id) {
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
    @ResponseBody Mono<ResponseEntity<?>> createComponent(@PathVariable Integer id, @RequestBody Component component) {
        Mono<Article> article = articleService.findById(id);
        return article.flatMap(a -> {
            if (component instanceof TextComponent) {
                return textComponentService.createComponent((TextComponent) component, id)
                        .map(c -> ResponseEntity.ok(new IdDto(c.getId())));
            }
            else if (component instanceof SurveyComponent) {
                return surveyComponentService.createComponent((SurveyComponent) component, id)
                        .map(c -> ResponseEntity.ok(new IdDto(c.getId())));
            }
            else if (component instanceof ImageComponent) {
                return imageComponentService.createComponent((ImageComponent) component, id)
                        .map(c -> ResponseEntity.ok(new IdDto(c.getId())));
            }
            else {
                return Mono.just(ResponseEntity.badRequest().build());
            }
        })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    private void incrementViewCounter(Integer articleId, String title) {
        meterRegistry.counter("article." + articleId + ".views", "articleTitle", title)
                .increment();
    }
}