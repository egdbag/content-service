package com.egdbag.content.service.core.model.survey;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, property="type")
@JsonSubTypes({
        @JsonSubTypes.Type(name="SINGLE_ANSWER", value= SingleAnswerQuestion.class),
        @JsonSubTypes.Type(name="MULTIPLE_ANSWERS", value= MultipleAnswersQuestion.class)
})
public abstract class Question {
}
