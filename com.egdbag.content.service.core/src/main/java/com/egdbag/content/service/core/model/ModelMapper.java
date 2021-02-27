package com.egdbag.content.service.core.model;

import com.egdbag.content.service.core.model.survey.Answer;
import com.egdbag.content.service.core.model.survey.Option;
import com.egdbag.content.service.core.model.survey.Question;
import com.egdbag.content.service.core.model.survey.SurveyComponent;
import com.egdbag.content.service.core.storage.schema.ArticleSchema;
import com.egdbag.content.service.core.storage.schema.CommentSchema;
import com.egdbag.content.service.core.storage.schema.ImageComponentSchema;
import com.egdbag.content.service.core.storage.schema.TextComponentSchema;
import com.egdbag.content.service.core.storage.schema.survey.AnswerSchema;
import com.egdbag.content.service.core.storage.schema.survey.OptionSchema;
import com.egdbag.content.service.core.storage.schema.survey.QuestionSchema;
import com.egdbag.content.service.core.storage.schema.survey.SurveyComponentSchema;
import org.springframework.stereotype.Service;

import java.net.URI;
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
        return toDto(textComponent, null);
    }

    public TextComponent toDto(TextComponentSchema textComponent, List<Comment> comments)
    {
        return TextComponent.builder().id(textComponent.getId())
                .text(textComponent.getText())
                .comments(comments)
                .build();
    }

    public TextComponentSchema toSchema(TextComponent textComponent, Integer articleId)
    {
        return TextComponentSchema.builder()
                .text(textComponent.getText())
                .articleId(articleId)
                .build();
    }

    public ImageComponent toDto(ImageComponentSchema imageComponent)
    {
        return ImageComponent.builder().id(imageComponent.getId())
                .uri(URI.create(imageComponent.getUri()))
                .build();
    }

    public ImageComponentSchema toSchema(ImageComponent imageComponent, Integer articleId)
    {
        return ImageComponentSchema.builder()
                .uri(imageComponent.getUri().toString())
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

    public Answer toDto(AnswerSchema answerSchema)
    {
        return Answer.builder()
                .id(answerSchema.getId())
                .userId(answerSchema.getUserId())
                .optionIds(splitOptions(answerSchema.getOptionIds()))
                .build();
    }

    public AnswerSchema toSchema(Answer answer, Integer userId, Integer questionId)
    {
        return AnswerSchema.builder()
                .questionId(questionId)
                .userId(userId)
                .optionIds(concatenateOptions(answer.getOptionIds()))
                .build();
    }

    public Comment toDto(CommentSchema commentSchema)
    {
        return Comment.builder()
                .id(commentSchema.getId())
                .userId(commentSchema.getUserId())
                .timestamp(commentSchema.getTs().getEpochSecond())
                .beginIndex(commentSchema.getBeginIndex())
                .endIndex(commentSchema.getEndIndex())
                .text(commentSchema.getText())
                .build();
    }

    public CommentSchema toSchema(Comment answer, Integer userId, Integer textComponentId)
    {
        return CommentSchema.builder()
                .userId(userId)
                .textComponentId(textComponentId)
                .text(answer.getText())
                .beginIndex(answer.getBeginIndex())
                .endIndex(answer.getEndIndex())
                .build();
    }

    public String concatenateOptions(List<Integer> optionIds)
    {
        return optionIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    private List<Integer> splitOptions(String optionIds)
    {
        return Arrays.asList(optionIds.split(","))
                .stream()
                .map(Integer::valueOf)
                .collect(Collectors.toList());
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
