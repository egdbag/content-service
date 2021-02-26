package com.egdbag.content.service.core.storage.schema.survey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("survey_components")
public class SurveyComponentSchema {
    @Id
    private Integer id;
    @Column("article_id")
    private Integer articleId;
}
