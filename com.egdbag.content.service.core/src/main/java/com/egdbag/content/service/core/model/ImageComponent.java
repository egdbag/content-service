package com.egdbag.content.service.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ImageComponent extends Component {
    private transient final String type = "IMAGE";
    private Integer id;
    private String url;
}
