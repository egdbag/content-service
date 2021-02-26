package com.egdbag.content.service.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Article {
    private Integer id;
    private String title;
    private long timestamp;
    private List<Component> components = new ArrayList<>();
}
