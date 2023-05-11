package com.zcore.mabokeserver.cast;

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
public class CastService {
    @Autowired
    private CastRepository castRepository;
    private Logger logger = LoggerFactory.getLogger(CastService.class);
    
    public Mono<ResponseEntity<Cast>> add(Cast cast) {
        return castRepository.save(cast).map(cast1 -> new ResponseEntity<>(cast1, HttpStatus.ACCEPTED))
        .defaultIfEmpty(new ResponseEntity<>(cast, HttpStatus.NOT_ACCEPTABLE));
    }

    public Flux<ResponseEntity<Cast>> getCast(/*Pageable pageable*/) {
        return castRepository.findAll()
        .map(cast -> new ResponseEntity<>(cast, HttpStatus.OK))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public Mono<ResponseEntity<Cast>> findById(String id) {
        return castRepository.findById(id)
        .map(cast -> new ResponseEntity<>(cast, HttpStatus.OK))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping
    public Mono<ResponseEntity<Cast>> updateCast(Cast cast) {
        String id = cast.getId();

        return castRepository.findById(cast.getId())
            .flatMap(cast1 -> {
                cast.setId(id);
                return castRepository.save(cast)
            .map(cast2 -> new ResponseEntity<>(cast2, HttpStatus.ACCEPTED));
        }).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public Mono<Void> deleteCast(String id) {
        return castRepository.deleteById(id);
    }
}