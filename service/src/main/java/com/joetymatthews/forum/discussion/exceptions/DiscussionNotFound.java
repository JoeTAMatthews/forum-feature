package com.joetymatthews.forum.discussion.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Discussion not found.")
public class DiscussionNotFound extends RuntimeException {}
