package com.joetymatthews.forum.thread.entity;

import com.devskiller.friendly_id.FriendlyId;
import com.joetymatthews.forum.thread.dto.ThreadDTO;
import com.joetymatthews.forum.thread.model.Comment;
import com.joetymatthews.forum.thread.model.Rating;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Document("threads")
public class Thread {

    @Id
    private String id;

    private String title;
    private String content;

    private List<Rating> ratings;
    private List<Comment> comments;

    private String userId;
    private long edited;
    private long created;

    @PersistenceConstructor
    public Thread(String id, String title, String content, String userId, long edited, long created) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.ratings = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.userId = userId;
        this.edited = edited;
        this.created = created;
    }

    public Thread(String title, String content, String userId) {
        this(FriendlyId.createFriendlyId(), title, content, userId, System.currentTimeMillis(), System.currentTimeMillis());
    }

    public Thread(ThreadDTO dto, String userId) {
        this(dto.title(), dto.content(), userId);
    }
}
