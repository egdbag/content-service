package com.egdbag.content.service.core.interfaces;

import com.egdbag.content.service.core.model.Comment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICommentService {
    Mono<Comment> createComment(Comment comment, Integer userId, Integer textComponentId);

    Mono<Comment> findById(Integer commentId);

    Mono<Comment> updateComment(Integer commentId, Comment comment);

    Mono<Comment> deleteComment(Integer componentId);

    Flux<Comment> getCommentsByTextComponentId(Integer textComponentId);
}
