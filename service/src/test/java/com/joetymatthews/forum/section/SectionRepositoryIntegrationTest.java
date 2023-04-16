package com.joetymatthews.forum.section;

import com.joetymatthews.forum.ForumApplication;
import com.joetymatthews.forum.TestUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {ForumApplication.class})
public class SectionRepositoryIntegrationTest {

    @Autowired
    private SectionRepository sectionRepository;

    private final Section section = TestUtil.createSection();

    @After
    public void tearDown() {
        sectionRepository.deleteAll().block();
    }

    @Test
    public void save_shouldReturnData() {
        Mono<Section> returned = sectionRepository.save(section);

        StepVerifier.create(returned)
                .assertNext(section1 -> {
                    assertThat(section1.getName()).isEqualTo("hi");
                })
                .verifyComplete();
    }

    @Test
    public void findById_shouldReturnEmpty() {
        Mono<Section> returned = sectionRepository.findById(section.getId());

        StepVerifier.create(returned)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void findById_shouldReturnData() {
        sectionRepository.save(section).block();

        Mono<Section> returned = sectionRepository.findById(section.getId());

        StepVerifier.create(returned)
                .assertNext(section1 -> {
                    assertThat(section1.getName()).isEqualTo("hi");
                })
                .verifyComplete();
    }

    @Test
    public void findByName_shouldReturnEmpty() {
        Mono<Section> returned = sectionRepository.findByName("hi");

        StepVerifier.create(returned)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void findByName_shouldReturnData() {
        sectionRepository.save(section).block();

        Mono<Section> returned = sectionRepository.findByName("hi");

        StepVerifier.create(returned)
                .assertNext(s1 -> {
                    assertThat(s1.getId()).isEqualTo(section.getId());
                    assertThat(s1.getName()).isEqualTo("hi");
                })
                .verifyComplete();
    }

    @Test
    public void findAll_shouldReturnTwo() {
        sectionRepository.save(section).block();
        sectionRepository.save(new Section("terminal", 1)).block();

        Flux<Section> returned = sectionRepository.findAll();

        StepVerifier.create(returned)
                .assertNext(s1 -> {
                    assertThat(s1.getName()).isEqualTo("hi");
                })
                .assertNext(s2 -> {
                    assertThat(s2.getName()).isEqualTo("terminal");
                })
                .verifyComplete();
    }

    @Test
    public void deleteById_shouldReturnEmpty() {
        Mono<Void> returned = sectionRepository.deleteById(section.getId());

        StepVerifier.create(returned)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void deleteById_shouldReturnData() {
        sectionRepository.save(section).block();

        Mono<Boolean> exists = sectionRepository.existsById(section.getId());

        StepVerifier.create(exists)
                .assertNext(ch -> assertThat(ch).isEqualTo(true))
                .verifyComplete();

        sectionRepository.deleteById(section.getId()).block();

        exists = sectionRepository.existsById(section.getId());

        StepVerifier.create(exists)
                .assertNext(ch -> assertThat(ch).isEqualTo(false))
                .verifyComplete();
    }
}
