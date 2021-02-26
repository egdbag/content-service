package com.egdbag.content.service.core.service;

import com.egdbag.content.service.core.model.Article;
import com.egdbag.content.service.core.model.Component;
import com.egdbag.content.service.core.model.TextComponent;
import com.egdbag.content.service.core.storage.schema.ArticleSchema;
import com.egdbag.content.service.core.storage.schema.TextComponentSchema;
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
