package com.joetymatthews.forum.discussion;

import com.joetymatthews.forum.TestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class DiscussionServiceUnitTest {

    private final DiscussionRepository discussionRepository = mock(DiscussionRepository.class);
    private final DiscussionService discussionService = new DiscussionService(discussionRepository);

    private final DiscussionDTO discussionDTO = TestUtil.createDiscussionDTO();
    private final Discussion discussion = TestUtil.createDiscussion();

    @Test
    public void createDiscussion_shouldReturnDiscussion() {
        when(discussionRepository.findBySectionIdAndTitle(anyString(), anyString())).thenReturn(Mono.empty());
        when(discussionRepository.save(any())).thenReturn(Mono.just(discussion));

        Mono<Discussion> returned = discussionService.createDiscussion(discussionDTO);

        verify(discussionRepository, times(1)).findBySectionIdAndTitle(anyString(), anyString());
        verify(discussionRepository, times(1)).save(any());
        verifyNoMoreInteractions(discussionRepository);

        StepVerifier.create(returned)
                .assertNext(TestUtil::assertDiscussion)
                .verifyComplete();
    }

    @Test
    public void getDiscussion_shouldReturnDiscussion() {
        when(discussionRepository.findById(anyString())).thenReturn(Mono.just(discussion));

        Mono<Discussion> returned = discussionService.getDiscussion("id");

        verify(discussionRepository, times(1)).findById(anyString());
        verifyNoMoreInteractions(discussionRepository);

        StepVerifier.create(returned)
                .assertNext(TestUtil::assertDiscussion)
                .verifyComplete();
    }

    @Test
    public void getDiscussionsBySectionId_shouldReturnDiscussions() {
        when(discussionRepository.findBySectionId(anyString())).thenReturn(TestUtil.createDiscussions());

        Flux<Discussion> returned = discussionService.getDiscussionsBySectionId("id");

        verify(discussionRepository, times(1)).findBySectionId(anyString());
        verifyNoMoreInteractions(discussionRepository);

        StepVerifier.create(returned)
                .assertNext(TestUtil::assertDiscussion)
                .assertNext(TestUtil::assertDiscussion)
                .verifyComplete();
    }

    @Test
    public void getDiscussions_shouldReturnDiscussions() {
        when(discussionRepository.findAll()).thenReturn(TestUtil.createDiscussions());

        Flux<Discussion> returned = discussionService.getDiscussions();

        verify(discussionRepository, times(1)).findAll();
        verifyNoMoreInteractions(discussionRepository);

        StepVerifier.create(returned)
                .assertNext(TestUtil::assertDiscussion)
                .assertNext(d -> {
                    assertThat(d.getTitle()).isEqualTo("Listen to them");
                    assertThat(d.getSectionId()).isEqualTo("1");
                })
                .verifyComplete();
    }

    @Test
    public void deleteDiscussion_shouldReturnDiscussion() {
        when(discussionRepository.findById(anyString())).thenReturn(Mono.just(discussion));
        when(discussionRepository.delete(any())).thenReturn(Mono.empty());

        Mono<Discussion> returned = discussionService.deleteDiscussion("id");

        verify(discussionRepository, times(1)).findById(anyString());
        verifyNoMoreInteractions(discussionRepository);

        StepVerifier.create(returned)
                .assertNext(TestUtil::assertDiscussion)
                .verifyComplete();
    }
}
