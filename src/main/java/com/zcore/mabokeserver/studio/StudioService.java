package com.zcore.mabokeserver.studio;

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
public class StudioService {
    @Autowired
    private StudioRepository studioRepository;
    private Logger logger = LoggerFactory.getLogger(StudioService.class);

    public Mono<ResponseEntity<Studio>> add(Studio studio) {
        return studioRepository.save(studio).map(studio1 -> new ResponseEntity<>(studio1, HttpStatus.ACCEPTED))
        .defaultIfEmpty(new ResponseEntity<>(studio, HttpStatus.NOT_ACCEPTABLE));
    }

    public Flux<ResponseEntity<Studio>> getStudios(/*Pageable pageable*/) {
        return studioRepository.findAll()
        .map(studios -> new ResponseEntity<>(studios, HttpStatus.OK))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public Mono<ResponseEntity<Studio>>  findById(String id) {
        return studioRepository.findById(id)
        .map(studio -> new ResponseEntity<>(studio, HttpStatus.OK))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping
    public Mono<ResponseEntity<Studio>> updateStudio(Studio studio) {
        String id = studio.getId();

        return studioRepository.findById(id)
            .flatMap(studio1 -> {
                studio.setId(id);
                return studioRepository.save(studio)
            .map(studio2 -> new ResponseEntity<>(studio2, HttpStatus.ACCEPTED));
        }).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public Mono<Void> deleteStudio(String id) {
        return studioRepository.deleteById(id);
    }
}
