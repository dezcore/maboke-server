package com.zcore.mabokeserver.view;

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
@RequestMapping("/view")
public class ViewController {
    @Autowired
    private ViewService viewService;

    @PostMapping
    public Mono<ResponseEntity<View>> add(@RequestBody View drive) throws Exception {
        return  viewService.add(drive);
    }

    @GetMapping
    public  Flux<ResponseEntity<View>> getDrives() {
        return viewService.getView();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<View>> getById(@PathVariable String id) {
        return viewService.findById(id);
    }

    @PutMapping
    public Mono<ResponseEntity<View>> updateView(@RequestBody View drive) {
        return viewService.updateView(drive);
    }

    @DeleteMapping("/{id}")
    public  Mono<Void> deleteView(@PathVariable("id") String id) {
        return viewService.deleteView(id);
    }
}
