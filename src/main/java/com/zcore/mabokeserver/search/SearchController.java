package com.zcore.mabokeserver.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private SearchService searchService;
    
    @PostMapping
    public Mono<ResponseEntity<Search>> add(@RequestBody Search search) throws Exception {
        return searchService.add(search);
    }

    @GetMapping
    public  Flux<ResponseEntity<Search>> getSearch() {
        return searchService.getSearch();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Search>> getById(@PathVariable String id) {
        return searchService.findById(id);
    }

    @PutMapping
    public Mono<ResponseEntity<Search>> updateSearch(@RequestBody Search search) {
        return searchService.updateSearch(search);
    }
    
    @DeleteMapping("/{id}")
    public  Mono<Void> deleteSearch(@PathVariable("id") String id) {
        return searchService.deleteSearch(id);
    }
}
