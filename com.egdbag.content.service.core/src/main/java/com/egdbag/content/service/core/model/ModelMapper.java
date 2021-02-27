package com.egdbag.content.service.core.model;

import com.egdbag.content.service.core.model.Article;
import com.egdbag.content.service.core.model.Component;
import com.egdbag.content.service.core.model.TextComponent;
import com.egdbag.content.service.core.model.survey.Option;
import com.egdbag.content.service.core.model.survey.Question;
import com.egdbag.content.service.core.model.survey.SurveyComponent;
import com.egdbag.content.service.core.storage.schema.ArticleSchema;
import com.egdbag.content.service.core.storage.schema.TextComponentSchema;
import com.egdbag.content.service.core.storage.schema.survey.OptionSchema;
import com.egdbag.content.service.core.storage.schema.survey.QuestionSchema;
import com.egdbag.content.service.core.storage.schema.survey.SurveyComponentSchema;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ModelMapper {

    public Article toDto(ArticleSchema articleSchema)
    {
        return toDto(articleSchema, null);
    }

    public Article toDto(ArticleSchema articleSchema, List<Component> components)
    {
        return Article.builder().id(articleSchema.getId())
                .title(articleSchema.getTitle())
                .timestamps(splitTimestamps(articleSchema.getTimestamps()))
                .hashtags(splitHashTags(articleSchema.getHashtags()))
                .components(components)
                .build();
    }

    public ArticleSchema toSchema(Article article)
    {
        return ArticleSchema.builder()
                .title(article.getTitle())
                .hashtags(article.getHashtags().stream().collect(Collectors.joining(",")))
                .build();
    }

    public TextComponent toDto(TextComponentSchema textComponent)
    {
        return TextComponent.builder().id(textComponent.getId())
                .text(textComponent.getText())
                .build();
    }

    public TextComponentSchema toSchema(TextComponent textComponent, Integer articleId)
    {
        return TextComponentSchema.builder()
                .text(textComponent.getText())
                .articleId(articleId)
                .build();
    }

    public SurveyComponent toDto(SurveyComponentSchema surveyComponent)
    {
        return toDto(surveyComponent, null);
    }

    public SurveyComponent toDto(SurveyComponentSchema surveyComponent, List<Question> questions)
    {
        return SurveyComponent.builder().id(surveyComponent.getId())
                .text(surveyComponent.getText())
                .questions(questions)
                .build();
    }

    public SurveyComponentSchema toSchema(SurveyComponent surveyComponent, Integer articleId)
    {
        return SurveyComponentSchema.builder()
                .text(surveyComponent.getText())
                .articleId(articleId)
                .build();
    }

    public Question toDto(QuestionSchema question)
    {
        return toDto(question, null);
    }

    public Question toDto(QuestionSchema question, List<Option> options)
    {
        return Question.builder().id(question.getId())
                .type(question.getType())
                .text(question.getText())
                .options(options)
                .build();
    }

    public QuestionSchema toSchema(Question question, Integer componentId)
    {
        return QuestionSchema.builder()
                .type(question.getType())
                .text(question.getText())
                .surveyComponentId(componentId)
                .build();
    }

    public Option toDto(OptionSchema option)
    {
        return Option.builder().id(option.getId())
                .text(option.getText())
                .build();
    }

    public OptionSchema toSchema(Option option, Integer questionId)
    {
        return OptionSchema.builder()
                .text(option.getText())
                .questionId(questionId)
                .build();
    }

    private Set<String> splitHashTags(String hashtags)
    {
        return Arrays.asList(hashtags.split(","))
                .stream()
                .collect(Collectors.toSet());
    }

    private List<Long> splitTimestamps(String timestamps)
    {
        return Arrays.asList(timestamps.split(","))
                .stream()
                .map(Long::valueOf)
                .collect(Collectors.toList());
    }
}
