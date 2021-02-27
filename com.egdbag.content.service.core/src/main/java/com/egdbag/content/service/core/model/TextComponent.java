package com.egdbag.content.service.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TextComponent extends Component {
    private transient final String type = "TEXT";
    private Integer id;
    private String text;
    private List<Comment> comments;

    public String getType() {
        return type;
    }
}
