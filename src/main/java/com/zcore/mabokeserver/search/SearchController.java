package com.zcore.mabokeserver.search;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zcore.mabokeserver.video.Video;

@RestController
@RequestMapping("/search")
public class SearchController {
    private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
    
    @GetMapping
    public List<Video> getVideo(@RequestParam(value = "name", defaultValue = "World") String name) {
		return null;//new Search(counter.incrementAndGet(),  String.format(template, name));
	}
}
