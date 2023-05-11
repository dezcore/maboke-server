package com.zcore.mabokeserver.search;

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
public class SearchService {
    @Autowired
    private SearchRepository searchRepository;
    private Logger logger = LoggerFactory.getLogger(SearchService.class);

    public Mono<ResponseEntity<Search>> add(Search search) {
        return searchRepository.save(search).map(search1 -> new ResponseEntity<>(search1, HttpStatus.ACCEPTED))
        .defaultIfEmpty(new ResponseEntity<>(search, HttpStatus.NOT_ACCEPTABLE));
    }

    public Flux<ResponseEntity<Search>> getSearch(/*Pageable pageable*/) {
        return searchRepository.findAll()
        .map(searchs -> new ResponseEntity<>(searchs, HttpStatus.OK))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public Mono<ResponseEntity<Search>> findById(String id) {
        return searchRepository.findById(id)
        .map(search -> new ResponseEntity<>(search, HttpStatus.OK))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping
    public Mono<ResponseEntity<Search>> updateSearch(Search search) {
        String id = search.getId();

        return searchRepository.findById(search.getId())
            .flatMap(search1 -> {
                search.setId(id);
                return searchRepository.save(search)
            .map(search2 -> new ResponseEntity<>(search2, HttpStatus.ACCEPTED));
        }).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    public Mono<Void> deleteSearch(String id) {
        return searchRepository.deleteById(id);
    }
}
