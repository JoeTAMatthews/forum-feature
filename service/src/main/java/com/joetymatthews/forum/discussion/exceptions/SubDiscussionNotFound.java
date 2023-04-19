package com.joetymatthews.forum.discussion.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Sub Discussion not found.")
public class SubDiscussionNotFound extends RuntimeException {}
