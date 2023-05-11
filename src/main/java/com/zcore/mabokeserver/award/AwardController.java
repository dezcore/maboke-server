package com.zcore.mabokeserver.award;

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
@RequestMapping("/award")
public class AwardController {
    @Autowired
    private AwardService awardService;
    
    @PostMapping
    public Mono<ResponseEntity<Award>> add(@RequestBody Award award) throws Exception {
        return  awardService.add(award);
    }

    @GetMapping
    public  Flux<ResponseEntity<Award>> getAward() {
        return awardService.getAward();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Award>> getById(@PathVariable String id) {
        return awardService.findById(id);
    }

    @PutMapping
    public Mono<ResponseEntity<Award>> updateAward(@RequestBody Award award) {
        return awardService.updateAward(award);
    }

    @DeleteMapping("/{id}")
    public  Mono<Void> deleteAward(@PathVariable("id") String id) {
        return awardService.deleteAward(id);
    }
}
