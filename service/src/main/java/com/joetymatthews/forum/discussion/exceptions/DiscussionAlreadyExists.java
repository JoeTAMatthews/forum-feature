package com.joetymatthews.forum.discussion.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Discussion already exists.")
public class DiscussionAlreadyExists extends RuntimeException {}
