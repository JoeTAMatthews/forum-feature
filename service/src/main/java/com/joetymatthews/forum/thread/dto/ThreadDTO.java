package com.joetymatthews.forum.thread.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThreadDTO {

    private String title;
    private String content;
}
