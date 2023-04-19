package com.joetymatthews.forum.discussion.sub;

import com.joetymatthews.forum.discussion.Discussion;
import com.joetymatthews.forum.discussion.DiscussionRepository;
import com.joetymatthews.forum.discussion.exceptions.DiscussionNotFound;
import com.joetymatthews.forum.discussion.exceptions.SubDiscussionNotFound;
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
        return discussionRepository.findById(dto.discussionId())
                .switchIfEmpty(Mono.error(DiscussionNotFound::new))
                .flatMap(discussion -> discussionRepository.save(discussion.addSub(dto)))
                .flatMap(discussion -> Mono.just(discussion.getSubByTitle(dto.title())));
    }

    public Mono<SubDiscussion> getSubDiscussion(String discussion, String id) {
        return discussionRepository.findById(discussion)
                .switchIfEmpty(Mono.defer(() -> Mono.error(DiscussionNotFound::new)))
                .flatMap(d -> {
                    if (!d.existsSub(id)) return Mono.error(SubDiscussionNotFound::new);

                    return Mono.just(d.getSub(id));
                });
    }

    public Flux<SubDiscussion> getSubDiscussionsByDiscussionId(String discussionId) {
        return discussionRepository.findAll()
                .filter(discussion -> discussion.getId().equals(discussionId))
                .flatMapIterable(Discussion::getSubs);
    }

    public Mono<SubDiscussion> deleteSubDiscussion(String discussion, String id) {
        return discussionRepository.findById(discussion)
                .switchIfEmpty(Mono.defer(() -> Mono.error(DiscussionNotFound::new)))
                .flatMap(d -> {
                    if (!d.existsSub(id)) return Mono.error(SubDiscussionNotFound::new);
                    SubDiscussion sub = d.getSub(id);
                    return discussionRepository.save(d.removeSub(id)).thenReturn(sub);
                });
    }
}
