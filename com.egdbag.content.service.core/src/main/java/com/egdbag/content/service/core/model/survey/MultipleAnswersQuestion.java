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
public class MultipleAnswersQuestion extends Question {
    private transient final String type = "MULTIPLE_ANSWERS";
    private Integer id;
    private String text;
    private List<Option> options;
}
