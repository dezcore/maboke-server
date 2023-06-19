package com.zcore.mabokeserver.serie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import reactor.core.publisher.Mono;

@Service
public class SerieService {
    @Autowired
    private SerieRepository serieRepository;
    private Logger logger = LoggerFactory.getLogger(SerieService.class);

    public Mono<ResponseEntity<Serie>> add(Serie serie) {
        return serieRepository.save(serie).map(serie1 -> new ResponseEntity<>(serie1, HttpStatus.ACCEPTED))
        .defaultIfEmpty(new ResponseEntity<>(serie, HttpStatus.NOT_ACCEPTABLE));
    }
    
    public Mono<Page<Serie>> getSerie(Pageable paging) {
        return this.serieRepository.count()
            .flatMap(serieCount -> {
                return this.serieRepository.findAll()
                    .skip((paging.getPageNumber()-1) * paging.getPageSize())
                    .take(paging.getPageSize())
                    .collectList().map(series -> new PageImpl<Serie>(series, paging, serieCount));
        });
    }

    public Mono<ResponseEntity<Serie>> findById(String id) {
        return serieRepository.findById(id)
        .map(serie -> new ResponseEntity<>(serie, HttpStatus.OK))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping
    public Mono<ResponseEntity<Serie>> updateSerie(Serie serie) {
        String id = serie.getId();

        return serieRepository.findById(serie.getId())
            .flatMap(serie1 -> {
                serie.setId(id);
                return serieRepository.save(serie)
            .map(serie2 -> new ResponseEntity<>(serie2, HttpStatus.ACCEPTED));
        }).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public Mono<Void> deleteSerie(String id) {
        return serieRepository.deleteById(id);
    }
}
