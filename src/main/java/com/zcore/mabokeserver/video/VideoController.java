package com.zcore.mabokeserver.video;

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
@RequestMapping("/video")
public class VideoController {
  @Autowired
    private VideoService videoService;

    @PostMapping
    public Mono<ResponseEntity<Video>> add(@RequestBody Video video) throws Exception {
        return  videoService.add(video);
    }

    @GetMapping
    public Flux<ResponseEntity<Video>> getVideo() {
        return videoService.getVideo();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Video>> getById(@PathVariable String id) {
        return videoService.findById(id);
    }

    @PutMapping
    public Mono<ResponseEntity<Video>> updateVideo(@RequestBody Video video) {
        return videoService.updateVideo(video);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteVideo(@PathVariable("id") String id) {
        return videoService.deleteVideo(id);
    }
}
