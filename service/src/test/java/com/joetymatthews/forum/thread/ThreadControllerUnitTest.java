package com.joetymatthews.forum.thread;

import com.joetymatthews.forum.TestUtil;
import com.joetymatthews.forum.thread.dto.ThreadDTO;
import com.joetymatthews.forum.thread.entity.Thread;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.reactive.ReactiveManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebFluxTest(value = ThreadController.class, excludeAutoConfiguration = {ReactiveSecurityAutoConfiguration.class, ReactiveManagementWebSecurityAutoConfiguration.class})
@AutoConfigureWebTestClient
public class ThreadControllerUnitTest {

    @Autowired
    private WebTestClient testClient;

    @MockBean
    private ThreadService threadService;

    private final Thread thread = TestUtil.thread();
    private final ThreadDTO dto = TestUtil.threadDTO();;

    @Test
    public void postThread_shouldReturnData() {
        when(threadService.createThread(any(), any())).thenReturn(Mono.just(thread));

        testClient.post()
                .uri("/thread")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(dto))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Thread.class)
                .isEqualTo(thread);

        verify(threadService, times(1)).createThread(any(), any());
        verifyNoMoreInteractions(threadService);
    }

    @Test
    public void getThread_shouldReturnData() {
        when(threadService.getThread(anyString())).thenReturn(Mono.just(thread));

        testClient.get()
                .uri("/thread/" + thread.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Thread.class)
                .isEqualTo(thread);

        verify(threadService, times(1)).getThread(anyString());
        verifyNoMoreInteractions(threadService);
    }

    @Test
    public void getThreadsByUser_shouldReturnData() {
        when(threadService.getThreadsByUser(anyString())).thenReturn(Flux.just(thread));

        testClient.get()
                .uri("/thread/user/user")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Thread.class)
                .contains(thread);

        verify(threadService, times(1)).getThreadsByUser(anyString());
        verifyNoMoreInteractions(threadService);
    }

    @Test
    public void getThreads_shouldReturnData() {
        when(threadService.getAll()).thenReturn(Flux.just(thread));

        testClient.get()
                .uri("/thread/all")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Thread.class)
                .contains(thread);

        verify(threadService, times(1)).getAll();
        verifyNoMoreInteractions(threadService);
    }

    @Test
    public void deleteThread_shouldReturnData() {
        when(threadService.removeThread(anyString())).thenReturn(Mono.just(thread));

        testClient.delete()
                .uri("/thread/" + thread.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Thread.class)
                .isEqualTo(thread);

        verify(threadService, times(1)).removeThread(thread.getId());
        verifyNoMoreInteractions(threadService);
    }
}
