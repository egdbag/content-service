package com.egdbag.article.core.storage.schema.survey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table("options")
public class OptionSchema {
    @Id
    private Integer id;
    private String text;
    @Column("question_id")
    private Integer questionId;
}