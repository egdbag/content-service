package com.egdbag.content.service.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment {
    private Integer id;
    private Integer userId;

    private String text;
    private Integer beginIndex;
    private Integer endIndex;
    private long timestamp;
}
