package com.zcore.mabokeserver.cast;

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
@RequestMapping("/cast")
public class CastController {
    @Autowired
    private CastService castService;
    
    @PostMapping
    public Mono<ResponseEntity<Cast>> add(@RequestBody Cast cast) throws Exception {
        return  castService.add(cast);
    }

    @GetMapping
    public  Flux<ResponseEntity<Cast>> getCast() {
        return castService.getCast();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Cast>> getById(@PathVariable String id) {
        return castService.findById(id);
    }

    @PutMapping
    public Mono<ResponseEntity<Cast>> updateAward(@RequestBody Cast cast) {
        return castService.updateCast(cast);
    }

    @DeleteMapping("/{id}")
    public  Mono<Void> deleteCast(@PathVariable("id") String id) {
        return castService.deleteCast(id);
    }
}
