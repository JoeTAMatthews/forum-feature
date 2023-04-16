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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {ForumApplication.class})
@AutoConfigureDataMongo
public class DiscussionRepositoryIntegrationTest {

    @Autowired
    private DiscussionRepository repository;

    private final DiscussionDTO dto = TestUtil.createDiscussionDTO();
    private final Discussion discussion = TestUtil.createDiscussion();
    private final Discussion discussion2 = new Discussion(new DiscussionDTO("1", "Listen to them"));

    @After
    public void tearDown() {
        repository.deleteAll().block();
    }

    @Test
    public void save_shouldSaveDiscussion() {
        Mono<Discussion> saved = repository.save(discussion);

        StepVerifier.create(saved)
                .assertNext(TestUtil::assertDiscussion)
                .verifyComplete();
    }

    @Test
    public void findById_shouldReturnDiscussion() {
        repository.save(discussion).block();

        Mono<Discussion> found = repository.findById(discussion.getId());

        StepVerifier.create(found)
                .assertNext(TestUtil::assertDiscussion)
                .verifyComplete();
    }

    @Test
    public void findBySectionId_shouldReturnDiscussions() {
        repository.save(discussion).block();
        repository.save(discussion2).block();

        Flux<Discussion> found = repository.findBySectionId(discussion.getSectionId());

        StepVerifier.create(found)
                .assertNext(TestUtil::assertDiscussion)
                .assertNext(d -> {
                    assertThat(d.getSectionId()).isEqualTo("1");
                    assertThat(d.getTitle()).isEqualTo("Listen to them");
                })
                .verifyComplete();
    }

    @Test
    public void findAll_shouldReturnDiscussions() {
        repository.save(discussion).block();
        repository.save(discussion2).block();

        Flux<Discussion> found = repository.findAll();

        StepVerifier.create(found)
                .assertNext(TestUtil::assertDiscussion)
                .assertNext(d -> {
                    assertThat(d.getSectionId()).isEqualTo("1");
                    assertThat(d.getTitle()).isEqualTo("Listen to them");
                })
                .verifyComplete();
    }

    @Test
    public void deleteById_shouldDeleteDiscussion() {
        repository.save(discussion).block();

        Mono<Boolean> exists = repository.existsById(discussion.getId());

        StepVerifier.create(exists)
                .assertNext(e -> assertThat(e).isTrue())
                .verifyComplete();

        repository.deleteById(discussion.getId()).block();

        exists = repository.existsById(discussion.getId());

        StepVerifier.create(exists)
                .assertNext(e -> assertThat(e).isFalse())
                .verifyComplete();
    }
}
