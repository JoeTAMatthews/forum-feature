package com.joetymatthews.forum.discussion;

import com.joetymatthews.forum.ForumApplication;
import com.joetymatthews.forum.TestUtil;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {ForumApplication.class})
@AutoConfigureDataMongo
public class DiscussionServiceIntegrationTest {

    @Autowired
    private DiscussionRepository discussionRepository;

    @Autowired
    private DiscussionService discussionService;

    private final Discussion discussion = TestUtil.createDiscussion();
    private final DiscussionDTO dto = TestUtil.createDiscussionDTO();

    @After
    public void tearDown() {
        discussionRepository.deleteAll().block();
    }

    @Test
    public void createDiscussion_shouldReturnDiscussion() {
        Mono<Discussion> returned = discussionService.createDiscussion(dto);

        StepVerifier.create(returned)
                .assertNext(TestUtil::assertDiscussion)
                .verifyComplete();
    }

    @Test
    public void getDiscussion_shouldReturnDiscussion() {
        discussionRepository.save(discussion).block();

        Mono<Discussion> returned = discussionService.getDiscussion(discussion.getId());

        StepVerifier.create(returned)
                .assertNext(TestUtil::assertDiscussion)
                .verifyComplete();
    }

    @Test
    public void getDiscussionsBySectionId_shouldReturnDiscussions() {
        discussionRepository.saveAll(TestUtil.createDiscussions()).blockLast();

        Flux<Discussion> returned = discussionService.getDiscussionsBySectionId(discussion.getSectionId());

        StepVerifier.create(returned)
                .expectNextCount(2)
                .verifyComplete();
    }
    @Test
    public void getDiscussions_shouldReturnDiscussions() {
        discussionRepository.saveAll(TestUtil.createDiscussions()).blockLast();

        Flux<Discussion> returned = discussionService.getDiscussions();

        StepVerifier.create(returned)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    public void deleteDiscussion_shouldReturnDiscussion() {
        discussionRepository.save(discussion).block();

        Mono<Discussion> returned = discussionService.deleteDiscussion(discussion.getId());

        StepVerifier.create(returned)
                .assertNext(TestUtil::assertDiscussion)
                .verifyComplete();
    }
}
