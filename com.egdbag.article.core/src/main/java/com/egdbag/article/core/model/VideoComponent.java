package com.egdbag.article.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VideoComponent extends Component {
    private transient final String type = "VIDEO";
    private Integer id;
}
