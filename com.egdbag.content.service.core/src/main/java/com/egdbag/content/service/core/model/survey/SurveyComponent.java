package com.egdbag.content.service.core.model.survey;

import com.egdbag.content.service.core.model.Component;
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
public class SurveyComponent extends Component {
    private transient final String type = "SURVEY";
    private Integer id;
    private String text;
    private List<Question> questions;
}
