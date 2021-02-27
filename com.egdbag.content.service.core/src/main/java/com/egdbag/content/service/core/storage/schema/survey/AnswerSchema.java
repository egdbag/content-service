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
@Table("answers")
public class AnswerSchema {
    @Id
    private Integer id;
    @Column("user_id")
    private Integer userId;
    @Column("question_id")
    private Integer questionId;
    private String optionIds;
}