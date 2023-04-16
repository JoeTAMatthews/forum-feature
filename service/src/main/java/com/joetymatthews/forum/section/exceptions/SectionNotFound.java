package com.joetymatthews.forum.section.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Section not found.")
public class SectionNotFound extends RuntimeException {}
