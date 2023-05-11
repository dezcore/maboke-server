package com.zcore.mabokeserver.movie;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;
    
    @PostMapping
    public Mono<ResponseEntity<Movie>> add(@RequestBody Movie movie) throws Exception {
        return movieService.add(movie);
    }

    @GetMapping
    public  Flux<ResponseEntity<Movie>> getMovie() {
        return movieService.getMovie();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Movie>> getById(@PathVariable String id) {
        return movieService.findById(id);
    }

    @PutMapping
    public Mono<ResponseEntity<Movie>> updateMovie(@RequestBody Movie movie) {
        return movieService.updateMovie(movie);
    }
    
    @DeleteMapping("/{id}")
    public  Mono<Void> deleteMovie(@PathVariable("id") String id) {
        return movieService.deleteMovie(id);
    }
}
