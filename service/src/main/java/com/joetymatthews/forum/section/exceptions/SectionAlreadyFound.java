package com.joetymatthews.forum.section.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FOUND, reason = "Section already found.")
public class SectionAlreadyFound extends RuntimeException {}
