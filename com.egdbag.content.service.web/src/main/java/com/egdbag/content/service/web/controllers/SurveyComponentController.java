package com.egdbag.content.service.web.controllers;

import com.egdbag.content.service.core.interfaces.*;
import com.egdbag.content.service.core.model.survey.Answer;
import com.egdbag.content.service.core.model.survey.Option;
import com.egdbag.content.service.core.model.survey.Question;
import com.egdbag.content.service.core.model.survey.SurveyComponent;
import com.egdbag.content.service.dto.IdDto;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("components/survey")
class SurveyComponentController {
    @Autowired
    private ISurveyComponentService surveyComponentService;
    @Autowired
    private IQuestionService questionService;
    @Autowired
    private IOptionService optionService;
    @Autowired
    private IAnswerService answerService;
    @Autowired
    private MeterRegistry meterRegistry;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200")
    Flux<SurveyComponent> getAll() {
        return surveyComponentService.getAllComponents();
    }

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
    Mono<ResponseEntity<IdDto>> createQuestion(@PathVariable Integer componentId, @RequestBody Question question) {
        return surveyComponentService.findById(componentId)
                .flatMap(c -> questionService.createQuestion(question, componentId)
                        .map(q ->ResponseEntity.ok(new IdDto(q.getId()))))
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

    @PostMapping(path = "/questions/{questionId}/options", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "201")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {
            @ExampleObject(value = "{\"text\": \"yes\"}")
    }))
    Mono<ResponseEntity<IdDto>> createOption(@PathVariable Integer questionId, @RequestBody Option option) {
        return questionService.findById(questionId)
                .flatMap(c -> optionService.createOption(option, questionId)
                        .map(o ->ResponseEntity.ok(new IdDto(o.getId()))))
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

    @PostMapping(path = "/questions/{questionId}/answers", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "201")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {
            @ExampleObject(value = "{\"optionIds\": [ 1, 2, 3]}")
    }))
    Mono<ResponseEntity<IdDto>> createAnswer(@PathVariable Integer questionId, @RequestBody Answer answer) {
        return questionService.findById(questionId)
                .flatMap(q -> answerService.createAnswer(answer, 1, questionId)
                        .map(a -> {
                            for (Integer optionId : answer.getOptionIds())
                            {
                                optionService.findById(optionId).subscribe(option -> incrementAnswerCounter(questionId, optionId, q.getText(), option.getText()));
                            }
                            return a;
                        })
                        .map(o ->ResponseEntity.ok(new IdDto(o.getId()))))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/questions/answers/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200")
    Mono<ResponseEntity<Answer>> getAnswerById(@PathVariable("id") Integer id) {
        return  answerService.findById(id)
                .map(u -> ResponseEntity.ok(u))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/questions/answers/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200")
    Mono<ResponseEntity<Answer>> updateAnswerById(@PathVariable Integer id, @RequestBody Answer answer) {
        return answerService.updateAnswer(id, answer)
                .map(updated -> ResponseEntity.ok(updated))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/questions/answers/{id}")
    @ApiResponse(responseCode = "204")
    Mono<ResponseEntity<Void>> deleteAnswerById(@PathVariable Integer id) {
        return answerService.deleteAnswer(id)
                .map( r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    private void incrementAnswerCounter(Integer questionId, Integer optionId, String questionText, String answerText) {
        meterRegistry.counter("question." + questionId + ".option." + optionId,
                "question", questionText, "option", answerText)
                .increment();
    }
}