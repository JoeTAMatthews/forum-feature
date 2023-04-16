package com.joetymatthews.forum.section;

import com.joetymatthews.forum.TestUtil;
import com.joetymatthews.forum.section.exceptions.SectionAlreadyFound;
import com.joetymatthews.forum.section.exceptions.SectionNotFound;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class SectionServiceUnitTest {

    private final SectionRepository sectionRepository = mock(SectionRepository.class);
    private final SectionService service = new SectionService(sectionRepository);

    private final Section section = TestUtil.createSection();
    private final SectionDTO dto = TestUtil.createSectionDTO();

    @Test
    public void saveSection_shouldReturnAlreadyFound() {
        when(sectionRepository.findByName(anyString())).thenReturn(Mono.just(section));

        Mono<Section> returned = service.saveSection(dto);

        verify(sectionRepository, times(1)).findByName(anyString());
        verifyNoMoreInteractions(sectionRepository);

        StepVerifier.create(returned)
                .expectError(SectionAlreadyFound.class)
                .verify();
    }

    @Test
    public void saveSection_shouldReturnData() {
        when(sectionRepository.findByName(anyString())).thenReturn(Mono.empty());
        when(sectionRepository.save(any())).thenReturn(Mono.just(section));

        Mono<Section> returned = service.saveSection(dto);

        verify(sectionRepository, times(1)).findByName(anyString());
        verifyNoMoreInteractions(sectionRepository);

        StepVerifier.create(returned)
                .assertNext(section1 -> {
                    assertThat(section1.getName()).isEqualTo("hi");
                    assertThat(section1.getOrder()).isEqualTo(0);
                })
                .verifyComplete();
    }

    @Test
    public void getSection_shouldReturnNotFound() {
        when(sectionRepository.findById(anyString())).thenReturn(Mono.empty());

        Mono<Section> returned = service.getSection("test");

        StepVerifier.create(returned)
                .expectError(SectionNotFound.class)
                .verify();
    }

    @Test
    public void getSection_shouldReturnData() {
        when(sectionRepository.findById(anyString())).thenReturn(Mono.just(section));

        Mono<Section> returned = service.getSection("hi");

        StepVerifier.create(returned)
                .assertNext(TestUtil::assertSection)
                .verifyComplete();
    }

    @Test
    public void getSections_shouldReturnTwo() {
        when(sectionRepository.findAll()).thenReturn(Flux.just(section, new Section("test", 1)));

        Flux<Section> returned = service.getSections();

        StepVerifier.create(returned)
                .assertNext(TestUtil::assertSection)
                .assertNext(section1 -> {
                    assertThat(section1.getName()).isEqualTo("test");
                    assertThat(section1.getOrder()).isEqualTo(1);
                })
                .verifyComplete();
    }

    @Test
    public void deleteSection_shouldReturnNotFound() {
        when(sectionRepository.findById(anyString())).thenReturn(Mono.empty());

        Mono<Section> returned = service.deleteSection("test");

        StepVerifier.create(returned)
                .expectError(SectionNotFound.class)
                .verify();
    }

    @Test
    public void deleteSection_shouldReturnData() {
        when(sectionRepository.findById(anyString())).thenReturn(Mono.just(section));
        when(sectionRepository.delete(any())).thenReturn(Mono.empty());

        Mono<Section> returned = service.deleteSection("test");

        StepVerifier.create(returned)
                .assertNext(TestUtil::assertSection)
                .verifyComplete();
    }
}
