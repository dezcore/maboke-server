package com.zcore.mabokeserver.video;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class VideoService {
    @Autowired
    private VideoRepository videoRepository;
    private Logger logger = LoggerFactory.getLogger(VideoService.class);

    public Mono<ResponseEntity<Video>> add(Video video) {
        return videoRepository.save(video).map(video1 -> new ResponseEntity<>(video1, HttpStatus.ACCEPTED))
        .defaultIfEmpty(new ResponseEntity<>(video, HttpStatus.NOT_ACCEPTABLE));
    }

    public Flux<ResponseEntity<Video>> getVideo(/*Pageable pageable*/) {
        return videoRepository.findAll()
        .map(videos -> new ResponseEntity<>(videos, HttpStatus.OK))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public Mono<ResponseEntity<Video>>  findById(String id) {
        return videoRepository.findById(id)
        .map(video -> new ResponseEntity<>(video, HttpStatus.OK))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping
    public Mono<ResponseEntity<Video>> updateVideo(Video video) {
        String id = video.getId();

        return videoRepository.findById(id)
            .flatMap(video1 -> {
                video.setId(id);
                return videoRepository.save(video)
            .map(video2 -> new ResponseEntity<>(video2, HttpStatus.ACCEPTED));
        }).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public Mono<Void> deleteVideo(String id) {
        return videoRepository.deleteById(id);
    }
}
