package com.zcore.mabokeserver.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zcore.mabokeserver.model.Video;

@RestController
public class GoogleController {
    private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
    
    @GetMapping("google/video")
    public Video getVideo(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Video(counter.incrementAndGet(),  String.format(template, name));
	}
}
