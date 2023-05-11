package com.zcore.mabokeserver.director;

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
@RequestMapping("/director")
public class DirectorController {
    @Autowired
    private DirectorService directorService;
    
    @PostMapping
    public Mono<ResponseEntity<Director>> add(@RequestBody Director director) throws Exception {
        return  directorService.add(director);
    }

    @GetMapping
    public  Flux<ResponseEntity<Director>> getDirector() {
        return directorService.getDirector();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Director>> getById(@PathVariable String id) {
        return directorService.findById(id);
    }

    @PutMapping
    public Mono<ResponseEntity<Director>> updateDirector(@RequestBody Director director) {
        return directorService.updateDirector(director);
    }

    @DeleteMapping("/{id}")
    public  Mono<Void> deleteDirector(@PathVariable("id") String id) {
        return directorService.deleteDirector(id);
    }
}
