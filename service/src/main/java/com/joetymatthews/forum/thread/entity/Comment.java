package com.joetymatthews.forum.thread.entity;

import com.devskiller.friendly_id.FriendlyId;
import com.joetymatthews.forum.thread.dto.CommentDTO;
import com.joetymatthews.forum.thread.model.Rating;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Document("thread_comments")
public class Comment {

    private String id;
    private String content;
    private List<Rating> ratings;

    private String threadId;
    private String replyCommentId;
    private String userId;
    private long edited;
    private long created;

    @PersistenceConstructor
    public Comment(String id, String content, String threadId, String replyCommentId, String userId, long edited, long created) {
        this.id = id;
        this.content = content;
        this.ratings = new ArrayList<>();
        this.threadId = threadId;
        this.replyCommentId = replyCommentId;
        this.userId = userId;
        this.edited = edited;
        this.created = created;
    }

    public Comment(String content, String threadId, String userId, String replyCommentId) {
        this(FriendlyId.createFriendlyId(), content, threadId, replyCommentId, userId, System.currentTimeMillis(), System.currentTimeMillis());
    }

    public Comment(CommentDTO dto, String userId) {
        this(dto.content(), dto.threadId(), dto.replyCommentId(), userId);
    }
}
