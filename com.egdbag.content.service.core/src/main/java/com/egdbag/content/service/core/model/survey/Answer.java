package com.egdbag.content.service.core.model.survey;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Answer {
    private Integer id;
    private Integer userId;
    private Integer questionId;
    private List<Integer> optionIds;
}
