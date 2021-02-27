package com.egdbag.content.service.web.controllers;

import com.egdbag.content.service.core.interfaces.*;
import com.egdbag.content.service.core.model.survey.Option;
import com.egdbag.content.service.core.model.survey.Question;
import com.egdbag.content.service.core.model.survey.SurveyComponent;
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
@RequestMapping("components/survey")
class SurveyComponentController {
    @Autowired
    private ISurveyComponentService surveyComponentService;
    @Autowired
    private IQuestionService questionService;
    @Autowired
    private IOptionService optionService;

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200")
    Mono<ResponseEntity<SurveyComponent>> getById(@PathVariable("id") Integer id) {
        return  surveyComponentService.findById(id)
                .map(u -> ResponseEntity.ok(u))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200")
    Mono<ResponseEntity<SurveyComponent>> updateById(@PathVariable Integer id, @RequestBody SurveyComponent component) {
        return surveyComponentService.updateComponent(id, component)
                .map(updated -> ResponseEntity.ok(updated))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    Mono<ResponseEntity<Void>> deleteById(@PathVariable Integer id) {
        return surveyComponentService.deleteComponent(id)
                .map( r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/{componentId}/questions", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "201")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {
            @ExampleObject(value = "{\"type\": \"SINGLE_ANSWER\", \"text\": \"Lorem ipsum dolor sit amet\"}"),
            @ExampleObject(value = "{\"type\": \"MULTIPLE_ANSWERS\", \"text\": \"Lorem ipsum dolor sit amet\"}")
    }))
    Mono<ResponseEntity<Object>> createQuestion(@PathVariable Integer componentId, @RequestBody Question question) {
        return surveyComponentService.findById(componentId)
                .flatMap(c -> questionService.createQuestion(question, componentId)
                        .map(q ->ResponseEntity.created(URI.create("/questions/" + q.getId())).build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/questions/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200")
    Mono<ResponseEntity<Question>> getQuestionById(@PathVariable("id") Integer id) {
        return  questionService.findById(id)
                .map(u -> ResponseEntity.ok(u))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/questions/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200")
    Mono<ResponseEntity<Question>> updateQuestionById(@PathVariable Integer id, @RequestBody Question question) {
        return questionService.updateQuestion(id, question)
                .map(updated -> ResponseEntity.ok(updated))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/questions/{id}")
    @ApiResponse(responseCode = "204")
    Mono<ResponseEntity<Void>> deleteQuestionById(@PathVariable Integer id) {
        return questionService.deleteQuestion(id)
                .map( r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/questions/{questionId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "201")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {
            @ExampleObject(value = "{\"text\": \"yes\"}")
    }))
    Mono<ResponseEntity<Object>> createOption(@PathVariable Integer questionId, @RequestBody Option option) {
        return questionService.findById(questionId)
                .flatMap(c -> optionService.createOption(option, questionId)
                        .map(o ->ResponseEntity.created(URI.create("/options/" + o.getId())).build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/questions/options/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200")
    Mono<ResponseEntity<Option>> getOptionById(@PathVariable("id") Integer id) {
        return  optionService.findById(id)
                .map(u -> ResponseEntity.ok(u))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/questions/options/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200")
    Mono<ResponseEntity<Option>> updateOptionById(@PathVariable Integer id, @RequestBody Option option) {
        return optionService.updateOption(id, option)
                .map(updated -> ResponseEntity.ok(updated))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/questions/options/{id}")
    @ApiResponse(responseCode = "204")
    Mono<ResponseEntity<Void>> deleteOptionById(@PathVariable Integer id) {
        return optionService.deleteOption(id)
                .map( r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}