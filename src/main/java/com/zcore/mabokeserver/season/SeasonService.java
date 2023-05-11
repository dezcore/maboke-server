package com.zcore.mabokeserver.season;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SeasonService {
    @Autowired
    private SeasonRepository seasonRepository;
    private Logger logger = LoggerFactory.getLogger(SeasonService.class);

    public Mono<ResponseEntity<Season>> add(Season season) {
        return seasonRepository.save(season).map(season1 -> new ResponseEntity<>(season1, HttpStatus.ACCEPTED))
        .defaultIfEmpty(new ResponseEntity<>(season, HttpStatus.NOT_ACCEPTABLE));
    }

    public Flux<ResponseEntity<Season>> getSeason(/*Pageable pageable*/) {
        return seasonRepository.findAll()
        .map(seasons -> new ResponseEntity<>(seasons, HttpStatus.OK))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public Mono<ResponseEntity<Season>> findById(String id) {
        return seasonRepository.findById(id)
        .map(season -> new ResponseEntity<>(season, HttpStatus.OK))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PutMapping
    public Mono<ResponseEntity<Season>> updateSeason(Season season) {
        String id = season.getId();

        return seasonRepository.findById(season.getId())
            .flatMap(season1 -> {
                season.setId(id);
                return seasonRepository.save(season)
            .map(season2 -> new ResponseEntity<>(season2, HttpStatus.ACCEPTED));
        }).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    public Mono<Void> deleteSeason(String id) {
        return seasonRepository.deleteById(id);
    }
}
