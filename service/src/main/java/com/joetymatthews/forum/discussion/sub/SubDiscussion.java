package com.joetymatthews.forum.discussion.sub;

import com.devskiller.friendly_id.FriendlyId;
import lombok.Data;

@Data
public class SubDiscussion {

    private String id;
    private String title;

    private long updated;
    private long created;

    public SubDiscussion(String id, String title, long updated, long created) {
        this.id = id;
        this.title = title;
        this.updated = updated;
        this.created = created;
    }

    public SubDiscussion(String title) {
        this(FriendlyId.createFriendlyId(), title, System.currentTimeMillis(), System.currentTimeMillis());
    }

    public SubDiscussion(SubDiscussionDTO dto) {
        this(dto.title());
    }
}
