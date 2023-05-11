package com.zcore.mabokeserver.director;

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
public class DirectorService {
    @Autowired
    private DirectorRepository directorRepository;
    private Logger logger = LoggerFactory.getLogger(DirectorService.class);

    public Mono<ResponseEntity<Director>> add(Director director) {
        return directorRepository.save(director).map(director1 -> new ResponseEntity<>(director1, HttpStatus.ACCEPTED))
        .defaultIfEmpty(new ResponseEntity<>(director, HttpStatus.NOT_ACCEPTABLE));
    }

    public Flux<ResponseEntity<Director>> getDirector(/*Pageable pageable*/) {
        return directorRepository.findAll()
        .map(directors -> new ResponseEntity<>(directors, HttpStatus.OK))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public Mono<ResponseEntity<Director>> findById(String id) {
        return directorRepository.findById(id)
        .map(director -> new ResponseEntity<>(director, HttpStatus.OK))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping
    public Mono<ResponseEntity<Director>> updateDirector(Director director) {
        String id = director.getId();

        return directorRepository.findById(director.getId())
            .flatMap(director1 -> {
                director.setId(id);
                return directorRepository.save(director)
            .map(director2 -> new ResponseEntity<>(director2, HttpStatus.ACCEPTED));
        }).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    public Mono<Void> deleteDirector(String id) {
        return directorRepository.deleteById(id);
    }
}
