package com.zcore.mabokeserver.serie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/serie")
public class SerieController {
    @Autowired
    private SerieService serieService;

    @PostMapping
    public Mono<ResponseEntity<Serie>> add(@RequestBody Serie serie) throws Exception {
        return  serieService.add(serie);
    }

    @GetMapping
    public Mono<Page<Serie>> getSeries(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
        Pageable paging = PageRequest.of(page, size);
        return serieService.getSerie(paging);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Serie>> getById(@PathVariable String id) {
        return serieService.findById(id);
    }

    @PutMapping
    public Mono<ResponseEntity<Serie>> updateView(@RequestBody Serie serie) {
        return serieService.updateSerie(serie);
    }
    
    @DeleteMapping("/{id}")
    public  Mono<Void> deleteView(@PathVariable("id") String id) {
        return serieService.deleteSerie(id);
    }
}
