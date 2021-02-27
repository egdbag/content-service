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
@Table("survey_components")
public class SurveyComponentSchema {
    @Id
    private Integer id;
    private String text;
    @Column("article_id")
    private Integer articleId;
}
