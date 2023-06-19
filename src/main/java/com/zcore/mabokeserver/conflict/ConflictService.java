package com.zcore.mabokeserver.conflict;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ConflictService {
    @Autowired
    private ConflictRepository conflictRepository;
    private Logger logger = LoggerFactory.getLogger(ConflictService.class);

    public Mono<ResponseEntity<Conflict>> add(Conflict conflict) {
        return this.conflictRepository.existsByOrignTitleAndConflictTitle(conflict.getOrignTitle(), conflict.getConflictTitle())
            .flatMap(exist -> {
                Mono<Conflict> moConflict = (!exist ? 
                    this.conflictRepository.save(conflict) : 
                    this.conflictRepository.findByOrignTitleAndConflictTitle(conflict.getOrignTitle(), conflict.getConflictTitle())
                );
                return moConflict.map(conflict2 -> new ResponseEntity<>(conflict2, HttpStatus.ACCEPTED));
        }).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    public Mono<Page<Conflict>> getNoMatchSerie(Pageable paging) {
        return this.conflictRepository.count()
            .flatMap(serieCount -> {
                return this.conflictRepository.findAll()
                    .skip((paging.getPageNumber()-1) * paging.getPageSize())
                    .take(paging.getPageSize())
                    .collectList().map(series -> new PageImpl<Conflict>(series, paging, serieCount));
        });
    }
}
