package com.zcore.mabokeserver.google.gdrive;


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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/gdrive")
public class GDriveController {
    @Autowired
    private GDriveService service;

    @GetMapping("/{name}")
    public Mono<ResponseEntity<GDrive>> getByName(@PathVariable String name) {
        return this.service.findByName(name);
    }

    @GetMapping("exist")
    public Mono<ResponseEntity<Boolean>> existByName(@RequestParam String name) {
        return this.service.existByName(name);
    }

    @GetMapping("/names")
    public Flux<GDrive> getByNames(@RequestParam String names) {
        return this.service.findByNames(names);
    }

    @PostMapping
    public Mono<ResponseEntity<GDrive>> add(@RequestBody GDrive drive) throws Exception {
        return this.service.addGFile(drive);
    }

    @PostMapping(value="/all")
    public Flux<GDrive> addAll(@RequestBody List<GDrive> drives) throws Exception {
        return this.service.addGFiles(drives);
    }

    @PutMapping
    public Mono<ResponseEntity<GDrive>> update(@RequestBody GDrive drive) {
        return this.service.update(drive);
    }

    @DeleteMapping("/{id}")
    public  Mono<Void> deleteView(@PathVariable("id") String id) {
        return this.service.delete(id);
    }

    @DeleteMapping("/all/{id}")
    public  Mono<Void> deleteAll(@RequestBody List<String> ids) {
        return this.service.deleteAll(ids);
    }
}
