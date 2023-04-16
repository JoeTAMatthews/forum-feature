package com.joetymatthews.forum.discussion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/discussion")
public class DiscussionController {

    private final DiscussionService service;

    @Autowired
    public DiscussionController(DiscussionService service) {
        this.service = service;
    }

    @PostMapping
    public Mono<Discussion> postDiscussion(@RequestBody DiscussionDTO dto) {
        return service.createDiscussion(dto);
    }

    @GetMapping("/{id}")
    public Mono<Discussion> getDiscussion(@PathVariable String id) {
        return service.getDiscussion(id);
    }

    @GetMapping("/section/{sectionId}")
    public Flux<Discussion> getDiscussionsBySectionId(@PathVariable String sectionId) {
        return service.getDiscussionsBySectionId(sectionId);
    }

    @GetMapping("/all")
    public Flux<Discussion> getDiscussions() {
        return service.getDiscussions();
    }

    @DeleteMapping("/{id}")
    public Mono<Discussion> deleteDiscussion(@PathVariable String id) {
        return service.deleteDiscussion(id);
    }
}
