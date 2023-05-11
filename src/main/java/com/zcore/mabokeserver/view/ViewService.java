package com.zcore.mabokeserver.view;

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
public class ViewService {
    @Autowired
    private ViewRepository viewRepository;
    private Logger logger = LoggerFactory.getLogger(ViewService.class);

    public Mono<ResponseEntity<View>> add(View view) {
        return viewRepository.save(view).map(view1 -> new ResponseEntity<>(view1, HttpStatus.ACCEPTED))
        .defaultIfEmpty(new ResponseEntity<>(view, HttpStatus.NOT_ACCEPTABLE));
    }

    public Flux<ResponseEntity<View>> getView(/*Pageable pageable*/) {
        return viewRepository.findAll()
        .map(views -> new ResponseEntity<>(views, HttpStatus.OK))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public Mono<ResponseEntity<View>> findById(String id) {
        return viewRepository.findById(id)
        .map(view -> new ResponseEntity<>(view, HttpStatus.OK))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping
    public Mono<ResponseEntity<View>> updateView(View view) {
        String id = view.getId();

        return viewRepository.findById(view.getId())
            .flatMap(view1 -> {
                view.setId(id);
                return viewRepository.save(view)
            .map(view2 -> new ResponseEntity<>(view2, HttpStatus.ACCEPTED));
        }).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public Mono<Void> deleteView(String id) {
        return viewRepository.deleteById(id);
    }
}
