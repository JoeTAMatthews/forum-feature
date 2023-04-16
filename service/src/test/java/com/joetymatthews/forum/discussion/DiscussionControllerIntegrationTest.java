package com.joetymatthews.forum.discussion;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.joetymatthews.forum.ForumApplication;
import com.joetymatthews.forum.TestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {ForumApplication.class})
@AutoConfigureDataMongo
public class DiscussionControllerIntegrationTest {

    @Autowired
    private WebTestClient testClient;

    @Autowired
    private DiscussionRepository discussionRepository;

    private final DiscussionDTO dto = TestUtil.createDiscussionDTO();
    private final Discussion discussion = TestUtil.createDiscussion();
    private final Gson gson = new GsonBuilder().create();

    @Test
    public void postDiscussion_shouldReturnDiscussion() {
        testClient.post().uri("/discussion")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(gson.toJson(dto)))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Discussion.class)
                .value(TestUtil::assertDiscussion);
    }

    @Test
    public void getDiscussion_shouldReturnDiscussion() {
        discussionRepository.save(discussion).block();

        testClient.get().uri("/discussion/" + discussion.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Discussion.class)
                .value(TestUtil::assertDiscussion);
    }

    @Test
    public void getDiscussionsBySectionId_shouldReturnDiscussions() {
        discussionRepository.saveAll(TestUtil.createDiscussions()).blockLast();

        testClient.get().uri("/discussion/section/" + discussion.getSectionId())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Discussion.class)
                .hasSize(2)
                .value(TestUtil::assertDiscussions);
    }

    @Test
    public void getDiscussions_shouldReturnDiscussions() {
        discussionRepository.saveAll(TestUtil.createDiscussions()).blockLast();

        testClient.get().uri("/discussion/all")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Discussion.class)
                .hasSize(2)
                .value(TestUtil::assertDiscussions);
    }

    @Test
    public void deleteDiscussions() {
        discussionRepository.save(discussion).block();

        testClient.delete().uri("/discussion/" + discussion.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Discussion.class)
                .value(TestUtil::assertDiscussion);
    }

}
