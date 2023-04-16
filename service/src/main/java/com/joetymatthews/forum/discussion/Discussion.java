package com.joetymatthews.forum.discussion;

import com.devskiller.friendly_id.FriendlyId;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document("discussions")
public class Discussion {

    @Id
    private String id;
    private String sectionId;
    private String title;

    private long updated;
    private long created;

    @PersistenceConstructor
    public Discussion(String id, String sectionId, String title, long updated, long created) {
        this.id = id;
        this.sectionId = sectionId;
        this.title = title;
        this.updated = updated;
        this.created = created;
    }

    public Discussion(String sectionId, String title) {
        this(FriendlyId.createFriendlyId(), sectionId, title, System.currentTimeMillis(), System.currentTimeMillis());
    }

    public Discussion(DiscussionDTO dto) {
        this(dto.sectionId(), dto.title());
    }

}
