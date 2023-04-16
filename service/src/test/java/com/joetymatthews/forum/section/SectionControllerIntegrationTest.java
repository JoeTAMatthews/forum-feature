package com.joetymatthews.forum.section;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.joetymatthews.forum.ForumApplication;
import com.joetymatthews.forum.TestUtil;
import org.junit.After;
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
public class SectionControllerIntegrationTest {

    @Autowired
    private WebTestClient client;

    @Autowired
    private SectionRepository sectionRepository;

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private SectionDTO dto = new SectionDTO("hi", 0);
    private Section section = TestUtil.createSection();

    @After
    public void tearDown() {
        sectionRepository.deleteAll().block();
    }

    @Test
    public void postSection_shouldReturnAlreadyFound() {
        sectionRepository.save(section).block();

        client.post()
                .uri("/section")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(gson.toJson(dto)))
                .exchange()
                .expectStatus().isFound()
                .expectBody()
                .consumeWith(System.out::println);

    }

    @Test
    public void postSection_shouldReturnData() {
        client.post()
                .uri("/section")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(gson.toJson(dto)))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.name").isEqualTo("hi")
                .jsonPath("$.order").isEqualTo(0);
    }

    @Test
    public void getSection_shouldReturnNotFound() {
        client.get()
                .uri("/section/1")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.message").isEmpty();
    }

    @Test
    public void getSection_shouldReturnData() {
        sectionRepository.save(section).block();

        client.get()
                .uri("/section/" + section.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("hi")
                .jsonPath("$.order").isEqualTo(0);
    }

    @Test
    public void getSectionByName_shouldReturnNotFound() {
        client.get()
                .uri("/section/name/creator")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.message").isEmpty();
    }

    @Test
    public void getSectionByName_shouldReturnData() {
        sectionRepository.save(section).block();

        client.get()
                .uri("/section/name/hi")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("hi")
                .jsonPath("$.order").isEqualTo(0);
    }

    @Test
    public void getSections_shouldReturnData() {
        sectionRepository.save(section).block();

        client.get()
                .uri("/section/all")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].name").isEqualTo("hi")
                .jsonPath("$[0].order").isEqualTo(0);
    }

    @Test
    public void deleteSection_shouldReturnNotFound() {
        client.delete()
                .uri("/section/1")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.message").isEmpty();
    }

    @Test
    public void deleteSection_shouldReturnData() {
        sectionRepository.save(section).block();

        client.delete()
                .uri("/section/" + section.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("hi")
                .jsonPath("$.order").isEqualTo(0);
    }
}
