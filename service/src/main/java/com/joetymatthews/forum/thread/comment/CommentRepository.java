package com.joetymatthews.forum.thread.comment;

import com.joetymatthews.forum.thread.entity.Comment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {

    Flux<Comment> findByThreadId(String threadId);

    Flux<Comment> findByUserId(String userId);

    Flux<Comment> findByReplyCommentId(String replyCommentId);

}
