package com.egdbag.content.service.core.model.survey;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.MessageFormat;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Question {
    private static final String SINGLE_ANSWER = "SINGLE_ANSWER";
    private static final String MULTIPLE_ANSWERS = "MULTIPLE_ANSWERS";

    private String type;
    private Integer id;
    private String text;
    private List<Option> options;

    public void setType(String type)
    {
        if (!SINGLE_ANSWER.equals(type) && !MULTIPLE_ANSWERS.equals(type)) {
            throw new IllegalStateException(
                    MessageFormat.format("Question must have {0} or {1} type", SINGLE_ANSWER, MULTIPLE_ANSWERS));
        }
        this.type = type;
    }
}
