package com.joetymatthews.forum.thread.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Rating {

    private String icon;
    private String userId;
    private long added;

    public Rating(String icon, String userId) {
        this.icon = icon;
        this.userId = userId;
        this.added = System.currentTimeMillis();
    }
}
