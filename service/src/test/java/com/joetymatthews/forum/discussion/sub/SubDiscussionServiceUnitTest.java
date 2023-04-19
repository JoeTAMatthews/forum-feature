package com.joetymatthews.forum.discussion.sub;

import com.joetymatthews.forum.TestUtil;
import com.joetymatthews.forum.discussion.Discussion;
import com.joetymatthews.forum.discussion.DiscussionRepository;
import com.joetymatthews.forum.discussion.DiscussionService;
import com.joetymatthews.forum.discussion.exceptions.DiscussionNotFound;
import com.joetymatthews.forum.discussion.exceptions.SubDiscussionNotFound;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class SubDiscussionServiceUnitTest {

    private final DiscussionRepository discussionRepository = mock(DiscussionRepository.class);
    private final SubDiscussionService service = new SubDiscussionService(discussionRepository);

    private final Discussion discussion = spy(TestUtil.createDiscussion());
    private final SubDiscussion subDiscussion = TestUtil.createSubDiscussion();
    private final SubDiscussionDTO dto = TestUtil.createSubDiscussionDTO();

    @Test
    public void createSubDiscussion_shouldReturnNotFound_whenIndexingDiscussion() {
        when(discussionRepository.findById(anyString())).thenReturn(Mono.empty());

        Mono<SubDiscussion> returned = service.createSubDiscussion(dto);

        verify(discussionRepository, times(1)).findById(anyString());
        verifyNoMoreInteractions(discussionRepository);

        StepVerifier.create(returned)
                .expectError(DiscussionNotFound.class)
                .verify();
    }

    @Test
    public void createSubDiscussion_shouldReturnData() {
        Discussion dis = TestUtil.createDiscussion();

        when(discussionRepository.findById(anyString())).thenReturn(Mono.just(dis));
        when(discussionRepository.save(any(Discussion.class))).thenReturn(Mono.just(dis.addSub(dto)));

        Mono<SubDiscussion> returned = service.createSubDiscussion(dto);

        verify(discussionRepository, times(1)).findById(anyString());
        verifyNoMoreInteractions(discussionRepository);

        StepVerifier.create(returned)
                .assertNext(TestUtil::assertSubDiscussion)
                .verifyComplete();
    }

    @Test
    public void getSubDiscussion_shouldReturnNotFound() {
        when(discussionRepository.findById(anyString())).thenReturn(Mono.empty());

        Mono<SubDiscussion> returned = service.getSubDiscussion("disc", "id");

        verify(discussionRepository, times(1)).findById(anyString());
        verifyNoMoreInteractions(discussionRepository);

        StepVerifier.create(returned)
                .expectError(DiscussionNotFound.class)
                .verify();
    }

    @Test
    public void getSubDiscussion_shouldReturnSubNotFound() {
        when(discussionRepository.findById(anyString())).thenReturn(Mono.just(discussion));

        Mono<SubDiscussion> returned = service.getSubDiscussion("disc", "id");

        verify(discussionRepository, times(1)).findById(anyString());
        verifyNoMoreInteractions(discussionRepository);

        StepVerifier.create(returned)
                .expectError(SubDiscussionNotFound.class)
                .verify();
    }

    @Test
    public void getSubDiscussion_shouldReturnData() {
        when(discussion.getSub(anyString())).thenReturn(subDiscussion);
        when(discussion.existsSub(anyString())).thenReturn(true);
        when(discussionRepository.findById(anyString())).thenReturn(Mono.just(discussion));

        Mono<SubDiscussion> returned = service.getSubDiscussion("disc", "1");

        verify(discussionRepository, times(1)).findById(anyString());
        verifyNoMoreInteractions(discussionRepository);

        StepVerifier.create(returned)
                .assertNext(TestUtil::assertSubDiscussion)
                .verifyComplete();
    }

    @Test
    public void getSubDiscussionsByDiscussionId_shouldReturnData() {
        when(discussion.getSubs()).thenReturn(new ArrayList<>(List.of(subDiscussion)));
        when(discussionRepository.findAll()).thenReturn(Flux.just(discussion));

        Flux<SubDiscussion> returned = service.getSubDiscussionsByDiscussionId(discussion.getId());

        verify(discussionRepository, times(1)).findAll();
        verifyNoMoreInteractions(discussionRepository);

        StepVerifier.create(returned)
                .assertNext(TestUtil::assertSubDiscussion)
                .verifyComplete();
    }

    @Test
    public void deleteSubDiscussion_shouldReturnDiscussionNotFound() {
        when(discussionRepository.findById(anyString())).thenReturn(Mono.empty());

        Mono<SubDiscussion> returned = service.deleteSubDiscussion("disc", "id");

        verify(discussionRepository, times(1)).findById(anyString());
        verifyNoMoreInteractions(discussionRepository);

        StepVerifier.create(returned)
                .expectError(DiscussionNotFound.class)
                .verify();
    }

    @Test
    public void deleteSubDiscussion_shouldThrowSubDiscussionNotFound() {
        when(discussionRepository.findById(anyString())).thenReturn(Mono.just(discussion));

        Mono<SubDiscussion> returned = service.deleteSubDiscussion("disc","id");

        verify(discussionRepository, times(1)).findById(anyString());
        verifyNoMoreInteractions(discussionRepository);

        StepVerifier.create(returned)
                .expectError(SubDiscussionNotFound.class)
                .verify();
    }

    @Test
    public void deleteSubDiscussion_shouldReturnData() {
        when(discussion.getSub(anyString())).thenReturn(subDiscussion);
        when(discussion.existsSub(anyString())).thenReturn(true);
        when(discussion.removeSub(anyString())).thenReturn(discussion);
        when(discussionRepository.findById(anyString())).thenReturn(Mono.just(discussion));
        when(discussionRepository.save(any(Discussion.class))).thenReturn(Mono.just(discussion));

        Mono<SubDiscussion> returned = service.deleteSubDiscussion("disc","id");

        verify(discussionRepository, times(1)).findById(anyString());
        verifyNoMoreInteractions(discussionRepository);

        StepVerifier.create(returned)
                .assertNext(TestUtil::assertSubDiscussion)
                .verifyComplete();
    }
}
