package com.joetymatthews.forum.thread.comment;

import com.joetymatthews.forum.thread.dto.CommentDTO;
import com.joetymatthews.forum.thread.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public Mono<Comment> postComment(ServerHttpRequest request, @RequestBody CommentDTO dto) {
        return commentService.addComment(dto, request);
    }

    @GetMapping("/{id}")
    public Mono<Comment> getComment(@PathVariable String id) {
        return commentService.getComment(id);
    }

    @GetMapping("/thread/{threadId}")
    public Flux<Comment> getCommentsByThread(@PathVariable String threadId) {
        return commentService.getCommentsByThread(threadId);
    }

    @GetMapping("/user/{userId}")
    public Flux<Comment> getCommentsByUser(@PathVariable String userId) {
        return commentService.getCommentsByUser(userId);
    }

    @GetMapping("/reply/{replyCommentId}")
    public Flux<Comment> getCommentsByReply(@PathVariable String replyCommentId) {
        return commentService.getCommentsByReply(replyCommentId);
    }

    @DeleteMapping("/{id}")
    public Mono<Comment> removeComment(@PathVariable String id) {
        return commentService.removeComment(id);
    }
 }
