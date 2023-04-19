package com.joetymatthews.forum.discussion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscussionDTO {

    private String sectionId;
    private String title;
}
