package com.egdbag.content.service.core.storage.schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("comments")
public class CommentSchema {
    @Id
    private Integer id;
    @Column("user_id")
    private Integer userId;
    @Column("text_component_id")
    private Integer textComponentId;

    private String text;
    private Instant ts;
    @Column("begin_index")
    private Integer beginIndex;
    @Column("end_index")
    private Integer endIndex;
}