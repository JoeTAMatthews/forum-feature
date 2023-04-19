package com.joetymatthews.forum.discussion;

import com.devskiller.friendly_id.FriendlyId;
import com.joetymatthews.forum.discussion.sub.SubDiscussion;
import com.joetymatthews.forum.discussion.sub.SubDiscussionDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@Document("discussions")
public class Discussion {

    @Id
    private String id;
    private String sectionId;
    private String title;

    private Map<String, SubDiscussion> subs;

    private long updated;
    private long created;

    @PersistenceConstructor
    public Discussion(String id, String sectionId, String title, long updated, long created) {
        this.id = id;
        this.sectionId = sectionId;
        this.title = title;
        this.subs = new HashMap<>();
        this.updated = updated;
        this.created = created;
    }

    public Discussion(String sectionId, String title) {
        this(FriendlyId.createFriendlyId(), sectionId, title, System.currentTimeMillis(), System.currentTimeMillis());
    }

    public Discussion(DiscussionDTO dto) {
        this(dto.getSectionId(), dto.getTitle());
    }

    public Discussion addSub(SubDiscussionDTO dto) {
        SubDiscussion sub = new SubDiscussion(dto);
        subs.put(sub.getId(), sub);
        return this;
    }

    public Discussion addSub(SubDiscussion sub) {
        subs.put(sub.getId(), sub);
        return this;
    }

    public List<SubDiscussion> getSubs() {
        return new ArrayList<>(subs.values());
    }

    public SubDiscussion getSub(String id) {
        return subs.get(id);
    }

    public SubDiscussion getSubByTitle(String title) {
        return subs.values().stream().filter(sub -> sub.getTitle().equals(title)).findFirst().orElse(null);
    }

    public Discussion removeSub(String id) {
        subs.remove(id);
        return this;
    }

    public boolean existsSub(String id) {
        return subs.containsKey(id);
    }
}
