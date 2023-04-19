package com.joetymatthews.forum.thread;

import com.joetymatthews.forum.ForumApplication;
import com.joetymatthews.forum.TestUtil;
import com.joetymatthews.forum.thread.dto.ThreadDTO;
import com.joetymatthews.forum.thread.entity.Thread;
import com.joetymatthews.forum.thread.exceptions.ThreadNotFound;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.reactive.ReactiveManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {ForumApplication.class})
@EnableAutoConfiguration(exclude = {ReactiveManagementWebSecurityAutoConfiguration.class, ReactiveSecurityAutoConfiguration.class})
@AutoConfigureDataMongo
public class ThreadServiceIntegrationTest {

    @Autowired
    private ThreadRepository threadRepository;

    @Autowired
    private ThreadService threadService;

    private final ThreadDTO dto = TestUtil.threadDTO();
    private final Thread thread = TestUtil.thread();
    private final ServerHttpRequest request = TestUtil.httpRequest();

    @After
    public void tearDown() {
        threadRepository.deleteAll().block();
    }

    @Test
    public void createThread_shouldReturnData() {
        Mono<Thread> returned = threadService.createThread(request, dto);

        StepVerifier.create(returned)
                .assertNext(TestUtil::assertThread)
                .verifyComplete();
    }

    @Test
    public void getThread_shouldThrowNotFound() {
        Mono<Thread> returned = threadService.getThread(thread.getId());

        StepVerifier.create(returned)
                .expectError(ThreadNotFound.class)
                .verify();
    }

    @Test
    public void getThread_shouldReturnData() {
        threadRepository.save(thread).block();

        Mono<Thread> returned = threadService.getThread(thread.getId());

        StepVerifier.create(returned)
                .assertNext(TestUtil::assertThread)
                .verifyComplete();
    }

    @Test
    public void getThreadsByUser_shouldReturnData() {
        threadRepository.save(thread).block();

        Flux<Thread> returned = threadService.getThreadsByUser("user");

        StepVerifier.create(returned)
                .assertNext(TestUtil::assertThread)
                .verifyComplete();
    }

    @Test
    public void getThreads_shouldReturnData() {
        threadRepository.save(thread).block();

        Flux<Thread> returned = threadService.getAll();

        StepVerifier.create(returned)
                .assertNext(TestUtil::assertThread)
                .verifyComplete();
    }

    @Test
    public void removeThread_shouldThrowNotFound() {
        Mono<Thread> returned = threadService.removeThread(thread.getId());

        StepVerifier.create(returned)
                .expectError(ThreadNotFound.class)
                .verify();
    }

    @Test
    public void removeThread_shouldReturnData() {
        threadRepository.save(thread).block();

        Mono<Thread> returned = threadService.removeThread(thread.getId());

        StepVerifier.create(returned)
                .assertNext(TestUtil::assertThread)
                .verifyComplete();
    }
}
