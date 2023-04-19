package com.joetymatthews.forum.discussion;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface DiscussionRepository extends ReactiveMongoRepository<Discussion, String> {

    Flux<Discussion> findBySectionId(String sectionId);

    Mono<Discussion> findBySectionIdAndTitle(String sectionId, String title);

    Mono<Discussion> findByTitle(String title);
}
