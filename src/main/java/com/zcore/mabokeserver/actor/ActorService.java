package com.zcore.mabokeserver.actor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ActorService {
    @Autowired
    private ActorRepository actorRepository;
    private Logger logger = LoggerFactory.getLogger(ActorService.class);

    public Mono<ResponseEntity<Actor>> add(Actor actor) {
        return actorRepository.save(actor).map(actor1 -> new ResponseEntity<>(actor1, HttpStatus.ACCEPTED))
        .defaultIfEmpty(new ResponseEntity<>(actor, HttpStatus.NOT_ACCEPTABLE));
    }

    public Flux<ResponseEntity<Actor>> getActor(/*Pageable pageable*/) {
        return actorRepository.findAll()
        .map(actors -> new ResponseEntity<>(actors, HttpStatus.OK))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public Mono<ResponseEntity<Actor>> findById(String id) {
        return actorRepository.findById(id)
        .map(actor -> new ResponseEntity<>(actor, HttpStatus.OK))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping
    public Mono<ResponseEntity<Actor>> updateActor(Actor actor) {
        String id = actor.getId();

        return actorRepository.findById(actor.getId())
            .flatMap(actor1 -> {
                actor.setId(id);
                return actorRepository.save(actor)
            .map(actor2 -> new ResponseEntity<>(actor2, HttpStatus.ACCEPTED));
        }).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public Mono<Void> deleteActor(String id) {
        return actorRepository.deleteById(id);
    }
}

