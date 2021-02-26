package com.egdbag.content.service.core.model.survey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SingleAnswerQuestion extends Question {
    private transient final String type = "SINGLE_ANSWER";
    private Integer id;
    private String text;
    private List<Option> options;
}