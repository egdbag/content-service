package com.egdbag.article.core.model.survey;

import com.egdbag.article.core.model.Component;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SurveyComponent extends Component {
    private transient final String type = "SURVEY";
    private Integer id;
    private List<Question> questions;
}