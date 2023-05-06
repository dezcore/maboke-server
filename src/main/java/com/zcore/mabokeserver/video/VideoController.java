package com.zcore.mabokeserver.video;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/video")
public class VideoController {
    private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
    
    @GetMapping
    public Video getVideo(@RequestParam(value = "name", defaultValue = "World") String name) {
		return null;//new Video(counter.incrementAndGet(),  String.format(template, name));
	}
}
