package com.egdbag.content.service.core.storage.schema;

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
@Table("image_components")
public class ImageComponentSchema {
    @Id
    private Integer id;
    private String uri;
    @Column("article_id")
    private Integer articleId;
}