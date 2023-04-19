package com.joetymatthews.forum.discussion;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.joetymatthews.forum.ForumApplication;
import com.joetymatthews.forum.TestUtil;
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
@EnableAutoConfiguration(exclude = { ReactiveSecurityAutoConfiguration.class, ReactiveManagementWebSecurityAutoConfiguration.class })
@AutoConfigureDataMongo
public class DiscussionControllerIntegrationTest {

    @Autowired
    private WebTestClient testClient;

    @Autowired
    private DiscussionRepository discussionRepository;

    private final DiscussionDTO dto = TestUtil.createDiscussionDTO();
    private final Discussion discussion = TestUtil.createDiscussion();
    private final Gson gson = new GsonBuilder().create();

    @After
    public void tearDown() {
        discussionRepository.deleteAll().block();
    }

    @Test
    public void postDiscussion_shouldReturnDiscussion() {
        testClient.post().uri("/discussion")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(gson.toJson(dto)))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.title").isEqualTo("How are you?")
                .jsonPath("$.sectionId").isEqualTo("1");
    }

    @Test
    public void getDiscussion_shouldReturnDiscussion() {
        discussionRepository.save(discussion).block();

        testClient.get().uri("/discussion/" + discussion.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.title").isEqualTo("How are you?")
                .jsonPath("$.sectionId").isEqualTo("1");
    }

    @Test
    public void getDiscussionsBySectionId_shouldReturnDiscussions() {
        discussionRepository.saveAll(TestUtil.createDiscussions()).blockLast();

        testClient.get().uri("/discussion/section/" + discussion.getSectionId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].title").isEqualTo("How are you?")
                .jsonPath("$[0].sectionId").isEqualTo("1");
    }

    @Test
    public void getDiscussions_shouldReturnDiscussions() {
        discussionRepository.saveAll(TestUtil.createDiscussions()).blockLast();

        testClient.get().uri("/discussion/all")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].title").isEqualTo("How are you?")
                .jsonPath("$[0].sectionId").isEqualTo("1");
    }

    @Test
    public void deleteDiscussion_shouldReturnData() {
        discussionRepository.save(discussion).block();

        testClient.delete().uri("/discussion/" + discussion.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.title").isEqualTo("How are you?")
                .jsonPath("$.sectionId").isEqualTo("1");
    }

}
