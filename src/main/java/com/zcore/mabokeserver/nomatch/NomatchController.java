package com.zcore.mabokeserver.nomatch;

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

import com.zcore.mabokeserver.video.Video;

import reactor.core.publisher.Mono;
@RestController
@RequestMapping("/nomatch")
public class NomatchController {
    @Autowired
    private NomatchService noMatchSerieService;

    @PostMapping
    public Mono<ResponseEntity<Nomatch>> add(@RequestBody Video video) throws Exception {
        return  noMatchSerieService.add(video);
    }

    @GetMapping
    public Mono<Page<Nomatch>> getNomatchSeries(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
        Pageable paging = PageRequest.of(page, size);
        return noMatchSerieService.getNoMatchSerie(paging);
    }

    @GetMapping("/{title}")
    public Mono<ResponseEntity<Nomatch>> getByTitle(@PathVariable String id) {
        return noMatchSerieService.findByTitle(id);
    }

    @PutMapping
    public Mono<ResponseEntity<Nomatch>> updateNomatch(@RequestBody Nomatch serie) {
        return noMatchSerieService.updateNomatch(serie);
    }
    
    @DeleteMapping("/{id}")
    public  Mono<Void> deleteNomatch(@PathVariable("id") String id) {
        return noMatchSerieService.deleteNomatch(id);
    }
}
