package com.zcore.mabokeserver.actor;

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
@RequestMapping("/actor")
public class ActorController {
    @Autowired
    private ActorService actorService;
    
    @PostMapping
    public Mono<ResponseEntity<Actor>> add(@RequestBody Actor actor) throws Exception {
        return  actorService.add(actor);
    }

    @GetMapping
    public  Flux<ResponseEntity<Actor>> getActor() {
        return actorService.getActor();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Actor>> getById(@PathVariable String id) {
        return actorService.findById(id);
    }

    @PutMapping
    public Mono<ResponseEntity<Actor>> updateActor(@RequestBody Actor actor) {
        return actorService.updateActor(actor);
    }

    @DeleteMapping("/{id}")
    public  Mono<Void> deleteActor(@PathVariable("id") String id) {
        return actorService.deleteActor(id);
    }
}
