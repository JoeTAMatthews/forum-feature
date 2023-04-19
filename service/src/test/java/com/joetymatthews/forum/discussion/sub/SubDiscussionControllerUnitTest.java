package com.joetymatthews.forum.discussion.sub;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.joetymatthews.forum.TestUtil;
import com.joetymatthews.forum.discussion.Discussion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.reactive.ReactiveManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebFluxTest(value = SubDiscussionController.class, excludeAutoConfiguration = {ReactiveManagementWebSecurityAutoConfiguration.class, ReactiveSecurityAutoConfiguration.class})
@AutoConfigureDataMongo
public class SubDiscussionControllerUnitTest {

    @Autowired
    private WebTestClient testClient;

    @MockBean
    private SubDiscussionService subDiscussionService;

    private final Discussion discussion = TestUtil.createDiscussion();
    private final SubDiscussion subDiscussion = TestUtil.createSubDiscussion();
    private final SubDiscussionDTO dto = TestUtil.createSubDiscussionDTO();
    private final Gson gson = new GsonBuilder().create();

    @Test
    public void postSubDiscussion_shouldReturnData() {
        when(subDiscussionService.createSubDiscussion(any(SubDiscussionDTO.class))).thenReturn(Mono.just(subDiscussion));

        testClient.post()
                .uri("/subdiscussion")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(gson.toJson(dto)))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.title").isEqualTo("How are you?");

        verify(subDiscussionService, times(1)).createSubDiscussion(any(SubDiscussionDTO.class));
        verifyNoMoreInteractions(subDiscussionService);
    }

    @Test
    public void getSubDiscussion_shouldReturnData() {
        when(subDiscussionService.getSubDiscussion(anyString(), anyString())).thenReturn(Mono.just(subDiscussion));

        testClient.get()
                .uri("/subdiscussion/{discussion}/{subDiscussionId}", discussion.getId(), subDiscussion.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.title").isEqualTo("How are you?");

        verify(subDiscussionService, times(1)).getSubDiscussion(anyString(), anyString());
        verifyNoMoreInteractions(subDiscussionService);
    }

    @Test
    public void getSubDiscussionsByDiscussionId_shouldReturnData() {
        when(subDiscussionService.getSubDiscussionsByDiscussionId(anyString())).thenReturn(Flux.fromArray(new SubDiscussion[]{subDiscussion}));

        testClient.get()
                .uri("/subdiscussion/{discussionId}", discussion.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].title").isEqualTo("How are you?");

        verify(subDiscussionService, times(1)).getSubDiscussionsByDiscussionId(anyString());
        verifyNoMoreInteractions(subDiscussionService);
    }

    @Test
    public void deleteSubDiscussion_shouldReturnData() {
        when(subDiscussionService.deleteSubDiscussion(anyString(), anyString())).thenReturn(Mono.just(subDiscussion));

        testClient.delete()
                .uri("/subdiscussion/{discussionId}/{subDiscussionId}", discussion.getId(), subDiscussion.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.title").isEqualTo("How are you?");

        verify(subDiscussionService, times(1)).deleteSubDiscussion(anyString(), anyString());
        verifyNoMoreInteractions(subDiscussionService);
    }
}
