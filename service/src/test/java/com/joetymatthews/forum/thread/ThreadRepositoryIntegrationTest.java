package com.joetymatthews.forum.thread;

import com.joetymatthews.forum.ForumApplication;
import com.joetymatthews.forum.TestUtil;
import com.joetymatthews.forum.thread.dto.ThreadDTO;
import com.joetymatthews.forum.thread.entity.Thread;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.reactive.ReactiveManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {ForumApplication.class})
@EnableAutoConfiguration(exclude = {ReactiveSecurityAutoConfiguration.class, ReactiveManagementWebSecurityAutoConfiguration.class})
public class ThreadRepositoryIntegrationTest {

    @Autowired
    private ThreadRepository threadRepository;

    private final Thread thread = TestUtil.thread();
    private final ThreadDTO dto = TestUtil.threadDTO();

    @Test
    public void save_shouldReturnData() {
        Mono<Thread> returned = threadRepository.save(thread);

        StepVerifier.create(returned)
                .assertNext(TestUtil::assertThread)
                .verifyComplete();
    }

    @Test
    public void findById_shouldReturnData() {
        Thread p = threadRepository.save(thread).block();

        assertNotNull(p);

        Mono<Thread> returned = threadRepository.findById(p.getId());

        StepVerifier.create(returned)
                .assertNext(TestUtil::assertThread)
                .verifyComplete();
    }

    @Test
    public void findByUserId_shouldReturnData() {
        Thread p = threadRepository.save(thread).block();

        assertNotNull(p);

        Mono<Thread> returned = threadRepository.findByUserId(p.getUserId()).next();

        StepVerifier.create(returned)
                .assertNext(TestUtil::assertThread)
                .verifyComplete();
    }

    @Test
    public void findAll_shouldReturnData() {
        threadRepository.save(thread).block();

        Mono<Thread> returned = threadRepository.findAll().next();

        StepVerifier.create(returned)
                .assertNext(TestUtil::assertThread)
                .verifyComplete();
    }

    @Test
    public void deleteById_shouldReturnData() {
        Thread p = threadRepository.save(thread).block();

        assertNotNull(p);

        Mono<Void> returned = threadRepository.deleteById(p.getId());

        StepVerifier.create(returned)
                .verifyComplete();
    }
}
