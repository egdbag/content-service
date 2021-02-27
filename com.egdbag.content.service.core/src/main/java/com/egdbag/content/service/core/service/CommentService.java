package com.egdbag.content.service.core.service;

import com.egdbag.content.service.core.interfaces.ICommentService;
import com.egdbag.content.service.core.model.Comment;
import com.egdbag.content.service.core.model.ModelMapper;
import com.egdbag.content.service.core.storage.repository.ICommentRepository;
import com.egdbag.content.service.core.storage.schema.CommentSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
public class CommentService implements ICommentService {
    @Autowired
    private ICommentRepository commentRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Mono<Comment> createComment(Comment comment, Integer userId, Integer textComponentId) {
        CommentSchema commentSchema = modelMapper.toSchema(comment, userId, textComponentId);
        commentSchema.setTs(Instant.now());
        return commentRepository.save(commentSchema)
                .map(modelMapper::toDto);
    }

    @Override
    public Mono<Comment> findById(Integer commentId) {
        return commentRepository.findById(commentId)
                .map(modelMapper::toDto);
    }

    @Override
    public Mono<Comment> updateComment(Integer commentId, Comment comment) {
        return commentRepository.findById(commentId)
                .flatMap(dbComment -> {
                    dbComment.setText(comment.getText());
                    return commentRepository.save(dbComment);
                })
                .map(modelMapper::toDto);
    }

    @Override
    public Mono<Comment> deleteComment(Integer commentId) {
        return commentRepository.findById(commentId)
                .flatMap(existingComment -> commentRepository.delete(existingComment)
                        .then(Mono.just(modelMapper.toDto(existingComment))));
    }

    @Override
    public Flux<Comment> getCommentsByTextComponentId(Integer textComponentId) {
        return commentRepository.findByTextComponentId(textComponentId)
                .map(modelMapper::toDto);
    }
}
