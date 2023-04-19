package com.joetymatthews.forum.thread.comment;

import com.joetymatthews.forum.thread.dto.CommentDTO;
import com.joetymatthews.forum.thread.entity.Comment;
import com.joetymatthews.forum.thread.exceptions.CommentNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Mono<Comment> addComment(CommentDTO dto, ServerHttpRequest request) {
        return commentRepository.save(new Comment(dto, "placeholder"));
    }

    public Mono<Comment> getComment(String id) {
        return commentRepository.findById(id)
                .switchIfEmpty(Mono.error(new CommentNotFound()));
    }

    public Flux<Comment> getCommentsByThread(String threadId) {
        return commentRepository.findByThreadId(threadId);
    }

    public Flux<Comment> getCommentsByUser(String userId) {
        return commentRepository.findByUserId(userId);
    }

    public Flux<Comment> getCommentsByReply(String replyCommentId) {
        return commentRepository.findByReplyCommentId(replyCommentId);
    }

    public Mono<Comment> removeComment(String id) {
        return commentRepository.findById(id)
                .switchIfEmpty(Mono.error(new CommentNotFound()))
                .flatMap(ret -> commentRepository.delete(ret).thenReturn(ret));
    }
}
