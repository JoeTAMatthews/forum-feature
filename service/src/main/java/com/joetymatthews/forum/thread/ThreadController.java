package com.joetymatthews.forum.thread;

import com.joetymatthews.forum.thread.dto.ThreadDTO;
import com.joetymatthews.forum.thread.entity.Thread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/thread")
public class ThreadController {

    private final ThreadService threadService;

    @Autowired
    public ThreadController(ThreadService threadService) {
        this.threadService = threadService;
    }

    @PostMapping
    public Mono<Thread> postThread(@RequestBody ThreadDTO dto, ServerHttpRequest request) {
        return threadService.createThread(request, dto);
    }

    @GetMapping("/{id}")
    public Mono<Thread> getThread(@PathVariable String id) {
        return threadService.getThread(id);
    }

    @GetMapping("/user/{id}")
    public Flux<Thread> getThreadsByUser(@PathVariable String id) {
        return threadService.getThreadsByUser(id);
    }

    @GetMapping("/all")
    public Flux<Thread> getAllThreads() {
        return threadService.getAll();
    }

    @DeleteMapping("/{id}")
    public Mono<Thread> deleteThread(@PathVariable String id) {
        return threadService.removeThread(id);
    }
}
