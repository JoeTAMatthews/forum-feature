package com.joetymatthews.forum.discussion.sub;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.joetymatthews.forum.ForumApplication;
import com.joetymatthews.forum.TestUtil;
import com.joetymatthews.forum.discussion.Discussion;
import com.joetymatthews.forum.discussion.DiscussionRepository;
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
@EnableAutoConfiguration(exclude = {ReactiveSecurityAutoConfiguration.class, ReactiveManagementWebSecurityAutoConfiguration.class})
@AutoConfigureDataMongo
public class SubDiscussionControllerIntegrationTest {

    @Autowired
    private WebTestClient testClient;

    @Autowired
    private DiscussionRepository discussionRepository;

    private final Discussion discussion = TestUtil.createDiscussion();
    private final SubDiscussionDTO dto = TestUtil.createSubDiscussionDTO(discussion);
    private final SubDiscussion subDiscussion = TestUtil.createSubDiscussion();
    private final Gson gson = new GsonBuilder().create();

    @After
    public void tearDown() {
        discussionRepository.deleteAll().block();
    }

    @Test
    public void postSubDiscussion_shouldReturnSubDiscussion() {
        discussionRepository.save(discussion).block();

        testClient.post()
                .uri("/subdiscussion")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(gson.toJson(dto)))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.title").isEqualTo("How are you?");
    }

    @Test
    public void getSubDiscussion_shouldReturnSubDiscussion() {
        discussionRepository.save(discussion.addSub(subDiscussion)).block();

        testClient.get()
                .uri("/subdiscussion/" + discussion.getId() + "/" + subDiscussion.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.title").isEqualTo("How are you?");
    }

    @Test
    public void getSubDiscussionsByDiscussionId_shouldReturnSubDiscussions() {
        discussionRepository.save(discussion.addSub(subDiscussion)).block();

        testClient.get().uri("/subdiscussion/" + discussion.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].title").isEqualTo("How are you?");
    }

    @Test
    public void deleteSubDiscussion_shouldReturnSubDiscussion() {
        discussionRepository.save(discussion.addSub(subDiscussion)).block();

        testClient.delete()
                .uri("/subdiscussion/" + discussion.getId() + "/" + subDiscussion.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.title").isEqualTo("How are you?");
    }
}
