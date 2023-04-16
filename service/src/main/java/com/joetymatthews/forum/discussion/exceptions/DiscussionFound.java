package com.joetymatthews.forum.discussion.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FOUND, reason = "Discussion already found.")
public class DiscussionFound extends RuntimeException {}
