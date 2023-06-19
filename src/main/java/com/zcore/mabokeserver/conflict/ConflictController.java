package com.zcore.mabokeserver.conflict;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;
@RestController
@RequestMapping("/conflict")
public class ConflictController {
    @Autowired
    private ConflictService conflictService;

    @PostMapping
    public Mono<ResponseEntity<Conflict>> add(@RequestBody Conflict conflict) throws Exception {
        return  this.conflictService.add(conflict);
    }

    @GetMapping
    public Mono<Page<Conflict>> getConflicts(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "3") int size) {
        Pageable paging = PageRequest.of(page, size);
        return conflictService.getNoMatchSerie(paging);
    }
}
