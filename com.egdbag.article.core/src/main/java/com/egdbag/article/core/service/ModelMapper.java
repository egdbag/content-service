package com.egdbag.article.core.service;

import com.egdbag.article.core.model.Article;
import com.egdbag.article.core.model.TextComponent;
import com.egdbag.article.core.storage.schema.ArticleSchema;
import com.egdbag.article.core.storage.schema.TextComponentSchema;
import org.springframework.stereotype.Service;

@Service
public class ModelMapper {
    public Article toDto(ArticleSchema articleSchema)
    {
        return Article.builder().id(articleSchema.getId())
                .title(articleSchema.getTitle())
                .timestamp(articleSchema.getTs().toEpochMilli())
                .build();
    }

    public ArticleSchema toSchema(Article article)
    {
        return ArticleSchema.builder()
                .title(article.getTitle())
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
}
