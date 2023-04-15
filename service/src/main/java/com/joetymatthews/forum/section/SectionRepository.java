package com.joetymatthews.forum.section;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface SectionRepository extends ReactiveMongoRepository<Section, String> {

    Mono<Section> findByName(String name);
}
