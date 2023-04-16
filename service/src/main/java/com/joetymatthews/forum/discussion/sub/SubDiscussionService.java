package com.joetymatthews.forum.discussion.sub;

import com.joetymatthews.forum.discussion.DiscussionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SubDiscussionService {

    private final DiscussionRepository discussionRepository;

    @Autowired
    public SubDiscussionService(DiscussionRepository discussionRepository) {
        this.discussionRepository = discussionRepository;
    }

    public Mono<SubDiscussion> createSubDiscussion(SubDiscussionDTO dto) {
        return Mono.empty();
    }

    public Mono<SubDiscussion> getSubDiscussion(String id) {
        return Mono.empty();
    }

    public Flux<SubDiscussion> getSubDiscussionsByDiscussionId(String discussionId) {
        return Flux.empty();
    }

    public Mono<SubDiscussion> deleteSubDiscussion(String id) {
        return Mono.empty();
    }
}
