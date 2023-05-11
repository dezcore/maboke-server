package com.zcore.mabokeserver.award;

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
public class AwardService {
    @Autowired
    private AwardRepository awardRepository;
    private Logger logger = LoggerFactory.getLogger(AwardService.class);

    public Mono<ResponseEntity<Award>> add(Award award) {
        return awardRepository.save(award).map(award1 -> new ResponseEntity<>(award1, HttpStatus.ACCEPTED))
        .defaultIfEmpty(new ResponseEntity<>(award, HttpStatus.NOT_ACCEPTABLE));
    }

    public Flux<ResponseEntity<Award>> getAward(/*Pageable pageable*/) {
        return awardRepository.findAll()
        .map(awards -> new ResponseEntity<>(awards, HttpStatus.OK))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public Mono<ResponseEntity<Award>> findById(String id) {
        return awardRepository.findById(id)
        .map(award -> new ResponseEntity<>(award, HttpStatus.OK))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping
    public Mono<ResponseEntity<Award>> updateAward(Award award) {
        String id = award.getId();

        return awardRepository.findById(award.getId())
            .flatMap(award1 -> {
                award.setId(id);
                return awardRepository.save(award)
            .map(award2 -> new ResponseEntity<>(award2, HttpStatus.ACCEPTED));
        }).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public Mono<Void> deleteAward(String id) {
        return awardRepository.deleteById(id);
    }
}
