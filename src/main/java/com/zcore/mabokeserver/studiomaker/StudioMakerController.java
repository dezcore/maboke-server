package com.zcore.mabokeserver.studiomaker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin
@RestController
@RequestMapping("/studiomaker")
public class StudioMakerController {
    @Autowired
    private StudioMakerService service;

    @PostMapping
    public ResponseEntity<String> add(@RequestBody Token token) throws Exception {
        return service.getToken(token.getCode(), token.getScope());
    }
    
    @GetMapping(value = "/files")
    public ResponseEntity<String> getFiles(@RequestBody Token token) {
        return service.getFiles(token.getAccessToken());
    }
}
