package com.joetymatthews.forum.discussion;

import com.joetymatthews.forum.discussion.exceptions.DiscussionAlreadyExists;
import com.joetymatthews.forum.discussion.exceptions.DiscussionNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DiscussionService {

    private final DiscussionRepository discussionRepository;

    @Autowired
    public DiscussionService(DiscussionRepository discussionRepository) {
        this.discussionRepository = discussionRepository;
    }

    public Mono<Discussion> createDiscussion(DiscussionDTO dto) {
        return discussionRepository.findBySectionIdAndTitle(dto.getSectionId(), dto.getTitle())
                .flatMap(discussion -> Mono.error(new DiscussionAlreadyExists())).cast(Discussion.class)
                .switchIfEmpty(discussionRepository.save(new Discussion(dto)));
    }

    public Mono<Discussion> getDiscussion(String id) {
        return discussionRepository.findById(id)
                .switchIfEmpty(Mono.error(new DiscussionNotFound()));
    }

    public Flux<Discussion> getDiscussionsBySectionId(String sectionId) {
        return discussionRepository.findBySectionId(sectionId);
    }

    public Flux<Discussion> getDiscussions() {
        return discussionRepository.findAll();
    }

    public Mono<Discussion> deleteDiscussion(String id) {
        return discussionRepository.findById(id)
                .switchIfEmpty(Mono.error(new DiscussionNotFound()))
                .flatMap(discussion -> discussionRepository.delete(discussion).thenReturn(discussion));
    }
}
