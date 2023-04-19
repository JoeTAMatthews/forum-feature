package com.joetymatthews.forum.discussion.sub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/subdiscussion")
public class SubDiscussionController {

    private final SubDiscussionService subDiscussionService;

    @Autowired
    public SubDiscussionController(SubDiscussionService subDiscussionService) {
        this.subDiscussionService = subDiscussionService;
    }

    @PostMapping
    public Mono<SubDiscussion> postSubDiscussion(@RequestBody SubDiscussionDTO dto) {
        return subDiscussionService.createSubDiscussion(dto);
    }

    @GetMapping("/{discussion}/{id}")
    public Mono<SubDiscussion> getSubDiscussion(@PathVariable String discussion, @PathVariable String id) {
        return subDiscussionService.getSubDiscussion(discussion, id);
    }

    @GetMapping("/{discussion}")
    public Flux<SubDiscussion> getSubDiscussionsByDiscussionId(@PathVariable String discussion) {
        return subDiscussionService.getSubDiscussionsByDiscussionId(discussion);
    }

    @DeleteMapping("/{discussion}/{id}")
    public Mono<SubDiscussion> deleteSubDiscussion(@PathVariable String discussion, @PathVariable String id) {
        return subDiscussionService.deleteSubDiscussion(discussion, id);
    }
}
