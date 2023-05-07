package com.zcore.mabokeserver.studiomaker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/studiomaker")
public class StudioMakerController {
    @Autowired
    private StudioMakerService service;

    @PostMapping
    public ResponseEntity<String> add(@RequestBody StudioMaker studio) throws Exception {
        return service.getToken(studio);
    }

    @GetMapping(value = "/files")
    public ResponseEntity<String> getFiles(@RequestBody String accessToken) {
        return service.getFiles(accessToken);
    }
}
