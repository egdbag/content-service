package com.egdbag.content.service.core.storage.schema.survey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("questions")
public class QuestionSchema {
    @Id
    private Integer id;
    private String text;
    private String type;
    @Column("survey_component_id")
    private Integer surveyComponentId;
}