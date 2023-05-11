package com.zcore.mabokeserver.studio;

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
@RequestMapping("/studio")
public class StudioController {
    @Autowired
    private StudioService studioService;

    @PostMapping
    public Mono<ResponseEntity<Studio>> add(@RequestBody Studio drive) throws Exception {
        return  studioService.add(drive);
    }

    @GetMapping
    public Flux<ResponseEntity<Studio>>  getStudio() {
        return studioService.getStudios();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Studio>> getById(@PathVariable String id) {
        return studioService.findById(id);
    }

    @PutMapping
    public Mono<ResponseEntity<Studio>> updateStudio(@RequestBody Studio studio) {
        return studioService.updateStudio(studio);
    }
    
    @DeleteMapping("/{id}")
    public Mono<Void> deleteStudio(@PathVariable("id") String id) {
        return studioService.deleteStudio(id);
    }
}
