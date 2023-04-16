package com.joetymatthews.forum.section;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/section")
public class SectionController {

    private final SectionService service;

    @Autowired
    public SectionController(SectionService service) {
        this.service = service;
    }

    @PostMapping
    public Mono<Section> postSection(@RequestBody SectionDTO dto) {
        return service.saveSection(dto);
    }

    @GetMapping("/{id}")
    public Mono<Section> getSection(@PathVariable String id) {
        return service.getSection(id);
    }

    @GetMapping("/name/{name}")
    public Mono<Section> getSectionByName(@PathVariable String name) {
        return service.getSectionByName(name);
    }

    @GetMapping("/all")
    public Flux<Section> getSections() {
        return service.getSections();
    }

    @DeleteMapping("/{id}")
    public Mono<Section> deleteSection(@PathVariable String id) {
        return service.deleteSection(id);
    }
}
