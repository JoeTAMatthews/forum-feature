package com.joetymatthews.forum.section;

import com.joetymatthews.forum.TestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.reactive.ReactiveManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebFluxTest(value = SectionController.class, excludeAutoConfiguration = {ReactiveSecurityAutoConfiguration.class, ReactiveManagementWebSecurityAutoConfiguration.class})
@AutoConfigureWebTestClient
public class SectionControllerUnitTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    public SectionService service;

    private SectionDTO dto = TestUtil.createSectionDTO();
    private Section section = TestUtil.createSection();

    @Test
    public void postSection_shouldReturnData() {
        Mockito.when(service.saveSection(any())).thenReturn(Mono.just(section));

        client.post()
                .uri("/section")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println);

        verify(service, times(1)).saveSection(any());
        verifyNoMoreInteractions(service);
    }

    @Test
    public void getSection_shouldReturnData() {
        when(service.getSection(anyString())).thenReturn(Mono.just(section));

        client.get().uri("/section/test")
                .exchange()
                .expectStatus().isOk();

        verify(service, times(1)).getSection(anyString());
        verifyNoMoreInteractions(service);
    }

    @Test
    public void getSectionByName_shouldReturnData() {
        when(service.getSectionByName("hi")).thenReturn(Mono.just(section));

        client.get().uri("/section/name/hi")
                .exchange()
                .expectStatus().isOk();

        verify(service, times(1)).getSectionByName(anyString());
        verifyNoMoreInteractions(service);
    }

    @Test
    public void getSections_shouldReturnData() {
        when(service.getSections()).thenReturn(Flux.just(section));

        client.get().uri("/section/all")
                .exchange()
                .expectStatus().isOk();

        verify(service, times(1)).getSections();
        verifyNoMoreInteractions(service);
    }

    @Test
    public void deleteSection_shouldReturnData() {
        when(service.deleteSection("1")).thenReturn(Mono.empty());

        client.delete().uri("/section/1")
                .exchange()
                .expectStatus().isOk();

        verify(service, times(1)).deleteSection(anyString());
        verifyNoMoreInteractions(service);
    }
}
