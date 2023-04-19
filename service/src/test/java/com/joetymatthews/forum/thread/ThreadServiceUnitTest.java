package com.joetymatthews.forum.thread;

import com.joetymatthews.forum.TestUtil;
import com.joetymatthews.forum.thread.dto.ThreadDTO;
import com.joetymatthews.forum.thread.entity.Thread;
import com.joetymatthews.forum.thread.exceptions.ThreadNotFound;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ThreadServiceUnitTest {

    private final ThreadRepository threadRepository = mock(ThreadRepository.class);
    private final ThreadService threadService = new ThreadService(threadRepository);

    private final Thread thread = TestUtil.thread();
    private final ThreadDTO dto = TestUtil.threadDTO();
    private final ServerHttpRequest request = TestUtil.httpRequest();

    @Test
    public void createThread_shouldReturnData() {
        when(threadRepository.save(any())).thenReturn(Mono.just(thread));

        Mono<Thread> returned = threadService.createThread(request, dto);

        StepVerifier.create(returned)
                .assertNext(TestUtil::assertThread)
                .verifyComplete();
    }

    @Test
    public void getThread_shouldThrowNotFound() {
        when(threadRepository.findById(anyString())).thenReturn(Mono.empty());

        Mono<Thread> returned = threadService.getThread(thread.getId());

        StepVerifier.create(returned)
                .expectError(ThreadNotFound.class)
                .verify();
    }

    @Test
    public void getThread_shouldReturnData() {
        when(threadRepository.findById(anyString())).thenReturn(Mono.just(thread));

        Mono<Thread> returned = threadService.getThread(thread.getId());

        StepVerifier.create(returned)
                .assertNext(TestUtil::assertThread)
                .verifyComplete();
    }

    @Test
    public void getThreadsByUser_shouldReturnData() {
        when(threadRepository.findByUserId(anyString())).thenReturn(Flux.just(thread));

        Mono<Thread> returned = threadService.getThreadsByUser(thread.getUserId()).next();

        StepVerifier.create(returned)
                .assertNext(TestUtil::assertThread)
                .verifyComplete();
    }

    @Test
    public void getAll_shouldReturnData() {
        when(threadRepository.findAll()).thenReturn(Flux.just(thread));

        Mono<Thread> returned = threadService.getAll().next();

        StepVerifier.create(returned)
                .assertNext(TestUtil::assertThread)
                .verifyComplete();
    }

    @Test
    public void removeThread_shouldThrowNotFound() {
        when(threadRepository.findById(anyString())).thenReturn(Mono.empty());

        Mono<Thread> returned = threadService.removeThread(thread.getId());

        StepVerifier.create(returned)
                .expectError(ThreadNotFound.class)
                .verify();
    }

    @Test
    public void removeThread_shouldReturnData() {
        when(threadRepository.findById(anyString())).thenReturn(Mono.just(thread));
        when(threadRepository.delete(any())).thenReturn(Mono.empty());

        Mono<Thread> returned = threadService.removeThread(thread.getId());

        StepVerifier.create(returned)
                .assertNext(TestUtil::assertThread)
                .verifyComplete();
    }
}
