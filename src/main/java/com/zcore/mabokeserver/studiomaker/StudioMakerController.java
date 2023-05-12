package com.zcore.mabokeserver.studiomaker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zcore.mabokeserver.studiomaker.mapper.dto.TokenDTO;

import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/studiomaker")
public class StudioMakerController {
    @Autowired
    private StudioMakerService service;
    private Logger logger = LoggerFactory.getLogger(StudioMakerService.class);

    @GetMapping(value = "/files")
    public Mono<String> getFiles(@RequestHeader(value="tokens")TokenDTO token) {
        return service.getFiles(token.getAccess_token());
    }

    @GetMapping(value = "/tokens")
    public Mono<TokenDTO> getAccessToken(@RequestBody TokenDTO token) throws Exception {
        return service.getAccessToken(token.getCode());
    }

    /*@GetMapping(value = "/files")
    public ResponseEntity<String> getDriveFiles(@RequestBody TokenDTO token) {
        return service.getFiles(token.getAccess_token());
    }*/
}
