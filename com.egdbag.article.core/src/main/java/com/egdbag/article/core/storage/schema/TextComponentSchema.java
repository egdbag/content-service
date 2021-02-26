package com.egdbag.article.core.storage.schema;

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
@Table("text_components")
public class TextComponentSchema {
    @Id
    private Integer id;
    private String text;
    @Column("article_id")
    private Integer articleId;
}