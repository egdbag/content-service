package com.egdbag.article.core.model;

import com.egdbag.article.core.model.survey.SurveyComponent;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, property="type")
@JsonSubTypes({
        @JsonSubTypes.Type(name="TEXT", value=TextComponent.class),
        @JsonSubTypes.Type(name="SURVEY", value= SurveyComponent.class),
        @JsonSubTypes.Type(name="IMAGE", value=ImageComponent.class),
        @JsonSubTypes.Type(name="VIDEO", value=VideoComponent.class)
})
public abstract class Component {
}
