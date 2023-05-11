package com.zcore.mabokeserver.movie;

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
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;
    private Logger logger = LoggerFactory.getLogger(MovieService.class);

    public Mono<ResponseEntity<Movie>> add(Movie director) {
        return movieRepository.save(director).map(director1 -> new ResponseEntity<>(director1, HttpStatus.ACCEPTED))
        .defaultIfEmpty(new ResponseEntity<>(director, HttpStatus.NOT_ACCEPTABLE));
    }

    public Flux<ResponseEntity<Movie>> getMovie(/*Pageable pageable*/) {
        return movieRepository.findAll()
        .map(movies -> new ResponseEntity<>(movies, HttpStatus.OK))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public Mono<ResponseEntity<Movie>> findById(String id) {
        return movieRepository.findById(id)
        .map(movie -> new ResponseEntity<>(movie, HttpStatus.OK))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping
    public Mono<ResponseEntity<Movie>> updateMovie(Movie movie) {
        String id = movie.getId();

        return movieRepository.findById(movie.getId())
            .flatMap(movie1 -> {
                movie.setId(id);
                return movieRepository.save(movie)
            .map(movie2 -> new ResponseEntity<>(movie2, HttpStatus.ACCEPTED));
        }).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    public Mono<Void> deleteMovie(String id) {
        return movieRepository.deleteById(id);
    }
}
