package com.joetymatthews.forum.section;

import com.devskiller.friendly_id.FriendlyId;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("sections")
public class Section {

    @Id
    private String id;
    private String name;
    private int order;

    private long updated;
    private final long created;

    @PersistenceConstructor
    public Section(String id, String name, long updated, long created) {
        this.id = id;
        this.name = name;
        this.updated = updated;
        this.created = created;
    }

    public Section(String name, int order) {
        this.id = FriendlyId.createFriendlyId();
        this.name = name;
        this.order = order;
        this.updated = System.currentTimeMillis();
        this.created = System.currentTimeMillis();
    }

    public Section(SectionDTO dto) {
        this(dto.name(), dto.order());
    }

    public void updateName(String name) {
        this.name = name;
        this.updated = System.currentTimeMillis();
    }
}
