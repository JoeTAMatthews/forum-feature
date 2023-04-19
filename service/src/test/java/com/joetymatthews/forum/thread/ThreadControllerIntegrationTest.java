package com.joetymatthews.forum.thread;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.joetymatthews.forum.ForumApplication;
import com.joetymatthews.forum.TestUtil;
import com.joetymatthews.forum.thread.dto.ThreadDTO;
import com.joetymatthews.forum.thread.entity.Thread;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.reactive.ReactiveManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {ForumApplication.class})
@EnableAutoConfiguration(exclude = {ReactiveManagementWebSecurityAutoConfiguration.class, ReactiveSecurityAutoConfiguration.class})
@AutoConfigureDataMongo
public class ThreadControllerIntegrationTest {

    @Autowired
    private WebTestClient testClient;

    @Autowired
    private ThreadRepository threadRepository;

    private final Thread thread = TestUtil.thread();
    private final ThreadDTO dto = TestUtil.threadDTO();
    private final Gson gson = new GsonBuilder().create();

    @After
    public void tearDown() {
        threadRepository.deleteAll().block();
    }

    @Test
    public void postThread_shouldReturn200() {
        testClient.post().uri("/thread")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(gson.toJson(dto)))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.title").isEqualTo(dto.title())
                .jsonPath("$.content").isEqualTo(dto.content());
    }

    @Test
    public void getThread_shouldReturn404() {
        testClient.get()
                .uri("/thread/id")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void getThread_shouldReturn200() {
        threadRepository.save(thread).block();

        testClient.get()
                .uri("/thread/" + thread.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.title").isEqualTo(dto.title())
                .jsonPath("$.content").isEqualTo(dto.content());
    }

    @Test
    public void getThreadsByUser_shouldReturn200() {
        threadRepository.save(thread).block();

        testClient.get()
                .uri("/thread/user/user")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$[0].title").isEqualTo(dto.title())
                .jsonPath("$[0].content").isEqualTo(dto.content());
    }

    @Test
    public void getAllThreads_shouldReturn200() {
        threadRepository.save(thread).block();

        testClient.get()
                .uri("/thread/all")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$[0].title").isEqualTo(dto.title())
                .jsonPath("$[0].content").isEqualTo(dto.content());
    }

    @Test
    public void deleteThread_shouldReturn404() {
        testClient.delete()
                .uri("/thread/id")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void deleteThread_shouldReturn200() {
        threadRepository.save(thread).block();

        testClient.delete()
                .uri("/thread/" + thread.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.title").isEqualTo(dto.title())
                .jsonPath("$.content").isEqualTo(dto.content());
    }
}
