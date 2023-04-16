package com.joetymatthews.forum.section;

import com.joetymatthews.forum.ForumApplication;
import com.joetymatthews.forum.TestUtil;
import com.joetymatthews.forum.section.exceptions.SectionAlreadyFound;
import com.joetymatthews.forum.section.exceptions.SectionNotFound;
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
public class SectionServiceIntegrationTest {

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private SectionService sectionService;

    private SectionDTO dto = TestUtil.createSectionDTO();
    private Section section = TestUtil.createSection();

    @After
    public void tearDown() {
        sectionRepository.deleteAll().block();
    }

    @Test
    public void saveSection_shouldReturnAlreadyFound() {
        sectionRepository.save(section).block();

        Mono<Section> returned = sectionService.saveSection(new SectionDTO("hi", 0));

        StepVerifier.create(returned)
                .expectError(SectionAlreadyFound.class)
                .verify();
    }

    @Test
    public void saveSection_shouldReturnData() {
        Mono<Section> returned = sectionService.saveSection(dto);

        StepVerifier.create(returned)
                .assertNext(section1 -> {
                    assertThat(section1.getName()).isEqualTo("creator");
                })
                .verifyComplete();
    }

    @Test
    public void getSection_shouldReturnEmpty() {
        Mono<Section> returned = sectionService.getSection(section.getId());

        StepVerifier.create(returned)
                .expectError(SectionNotFound.class)
                .verify();
    }

    @Test
    public void getSection_shouldReturnData() {
        sectionRepository.save(section).block();

        Mono<Section> returned = sectionService.getSection(section.getId());

        StepVerifier.create(returned)
                .assertNext(section1 -> {
                    assertThat(section1.getName()).isEqualTo("hi");
                })
                .verifyComplete();
    }

    @Test
    public void getSections_shouldReturnEmpty() {
        Flux<Section> returned = sectionService.getSections();

        StepVerifier.create(returned)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void getSections_shouldReturnData() {
        sectionRepository.save(section).block();

        Flux<Section> returned = sectionService.getSections();

        StepVerifier.create(returned)
                .assertNext(section1 -> {
                    assertThat(section1.getName()).isEqualTo("hi");
                })
                .verifyComplete();
    }

    @Test
    public void deleteSection_shouldReturnEmpty() {
        Mono<Section> returned = sectionService.deleteSection(section.getId());

        StepVerifier.create(returned)
                .expectError(SectionNotFound.class)
                .verify();
    }

    @Test
    public void deleteSection_shouldReturnData() {
        sectionRepository.save(section).block();

        Mono<Section> returned = sectionService.deleteSection(section.getId());

        StepVerifier.create(returned)
                .assertNext(section1 -> {
                    assertThat(section1.getName()).isEqualTo("hi");
                })
                .verifyComplete();
    }
}
