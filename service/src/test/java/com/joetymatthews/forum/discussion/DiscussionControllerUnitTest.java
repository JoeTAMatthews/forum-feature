package com.joetymatthews.forum.discussion;

import com.google.gson.GsonBuilder;
import com.joetymatthews.forum.TestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.reactive.ReactiveManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebFluxTest(value = DiscussionController.class, excludeAutoConfiguration = { ReactiveSecurityAutoConfiguration.class, ReactiveManagementWebSecurityAutoConfiguration.class })
@AutoConfigureWebTestClient
public class DiscussionControllerUnitTest {

    @Autowired
    private WebTestClient testClient;

    @MockBean
    private DiscussionService service;

    private final DiscussionDTO dto = TestUtil.createDiscussionDTO();
    private final Discussion discussion = TestUtil.createDiscussion();

    @Test
    public void postDiscussion_shouldReturnDiscussion() {
        when(service.createDiscussion(any(DiscussionDTO.class))).thenReturn(Mono.just(discussion));

        testClient.post().uri("/discussion")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(new GsonBuilder().create().toJson(dto)))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Discussion.class)
                .consumeWith(System.out::println)
                .value(TestUtil::assertDiscussion);

        verify(service).createDiscussion(any(DiscussionDTO.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    public void getDiscussion_shouldReturnDiscussion() {
        when(service.getDiscussion(any(String.class))).thenReturn(Mono.just(discussion));

        testClient.get().uri("/discussion/" + discussion.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Discussion.class)
                .value(TestUtil::assertDiscussion);

        verify(service, times(1)).getDiscussion(any(String.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    public void getDiscussionsBySectionId_shouldReturnDiscussions() {
        when(service.getDiscussionsBySectionId(any(String.class))).thenReturn(TestUtil.createDiscussions());

        testClient.get().uri("/discussion/section/" + discussion.getSectionId())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Discussion.class)
                .value(TestUtil::assertDiscussions);

        verify(service, times(1)).getDiscussionsBySectionId(any(String.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    public void getDiscussions_shouldReturnDiscussions() {
        when(service.getDiscussions()).thenReturn(TestUtil.createDiscussions());

        testClient.get().uri("/discussion/all")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Discussion.class)
                .value(TestUtil::assertDiscussions);

        verify(service, times(1)).getDiscussions();
        verifyNoMoreInteractions(service);
    }

    @Test
    public void deleteDiscussion_shouldReturnDiscussion() {
        when(service.deleteDiscussion(any(String.class))).thenReturn(Mono.just(discussion));

        testClient.delete().uri("/discussion/" + discussion.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Discussion.class)
                .value(TestUtil::assertDiscussion);

        verify(service, times(1)).deleteDiscussion(any(String.class));
        verifyNoMoreInteractions(service);
    }
}
