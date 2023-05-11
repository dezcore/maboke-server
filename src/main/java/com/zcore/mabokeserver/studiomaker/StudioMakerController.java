package com.zcore.mabokeserver.studiomaker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zcore.mabokeserver.studiomaker.mapper.dto.TokenDTO;

import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;

//@CrossOrigin
@RestController
@RequestMapping("/studiomaker")
public class StudioMakerController {
    @Autowired
    private StudioMakerService service;
    private Logger logger = LoggerFactory.getLogger(StudioMakerService.class);

    @PostMapping
    public Mono<String>  add(@RequestBody GToken token) throws Exception {
        return service.getToken(token.getCode(), token.getScope());
    }

    @GetMapping(value = "/files")
    public ResponseEntity<String> getFiles(@RequestBody GToken token) {
        return service.getFiles(token.getAccessToken());
    }
}
