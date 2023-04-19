package com.joetymatthews.forum.thread;

import com.joetymatthews.forum.thread.dto.ThreadDTO;
import com.joetymatthews.forum.thread.entity.Thread;
import com.joetymatthews.forum.thread.exceptions.ThreadNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ThreadService {

    private final ThreadRepository threadRepository;

    @Autowired
    public ThreadService(ThreadRepository threadRepository) {
        this.threadRepository = threadRepository;
    }

    public Mono<Thread> createThread(ServerHttpRequest request, ThreadDTO dto) {
        return threadRepository.save(new Thread(dto, "user"));
    }

    public Mono<Thread> getThread(String id) {
        return threadRepository.findById(id)
                .switchIfEmpty(Mono.error(new ThreadNotFound()));
    }

    public Flux<Thread> getThreadsByUser(String id) {
        return threadRepository.findByUserId(id);
    }

    public Flux<Thread> getAll() {
        return threadRepository.findAll();
    }

    public Mono<Thread> removeThread(String id) {
        return threadRepository.findById(id)
                .switchIfEmpty(Mono.error(new ThreadNotFound()))
                .flatMap(th -> threadRepository.delete(th).thenReturn(th));
    }
}
