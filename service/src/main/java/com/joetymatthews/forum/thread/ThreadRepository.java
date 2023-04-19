package com.joetymatthews.forum.thread;

import com.joetymatthews.forum.thread.entity.Thread;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ThreadRepository extends ReactiveMongoRepository<Thread, String> {

    Flux<Thread> findByUserId(String userId);
}
