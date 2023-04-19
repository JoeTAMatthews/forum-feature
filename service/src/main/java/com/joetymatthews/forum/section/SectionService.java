package com.joetymatthews.forum.section;

import com.joetymatthews.forum.section.exceptions.SectionAlreadyFound;
import com.joetymatthews.forum.section.exceptions.SectionNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;

    @Autowired
    public SectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    public Mono<Section> saveSection(SectionDTO dto) {
        return sectionRepository.findByName(dto.getName())
                .flatMap(section -> Mono.defer(() -> Mono.error(SectionAlreadyFound::new))).cast(Section.class)
                .switchIfEmpty(Mono.defer(() -> this.sectionRepository.save(new Section(dto))));
    }

    public Mono<Section> getSection(String id) {
        return sectionRepository.findById(id)
                .switchIfEmpty(Mono.defer(() -> Mono.error(SectionNotFound::new)));
    }

    public Mono<Section> getSectionByName(String name) {
        return sectionRepository.findByName(name)
                .switchIfEmpty(Mono.defer(() -> Mono.error(SectionNotFound::new)));
    }

    public Flux<Section> getSections() {
        return sectionRepository.findAll();
    }

    public Mono<Section> deleteSection(String id) {
        return sectionRepository.findById(id)
                .switchIfEmpty(Mono.defer(() -> Mono.error(SectionNotFound::new)))
                .flatMap(section -> sectionRepository.delete(section).thenReturn(section));
    }
}
