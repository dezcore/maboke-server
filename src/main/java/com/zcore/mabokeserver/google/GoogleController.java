package com.zcore.mabokeserver.google;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zcore.mabokeserver.studiomaker.mapper.dto.TokenDTO;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/google")
public class GoogleController {
    @Autowired
    private DriveService service;

    @GetMapping(value = "/token")
    public Mono<TokenDTO> getToken(@RequestBody TokenDTO token) {
        try {
            return service.getAccessToken(token.getCode());
        } catch (URISyntaxException | IOException | GeneralSecurityException e) {
            e.printStackTrace();
            //new ResponseEntity<>(HttpStatus.NOT_FOUND)
            return null;//ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/refreshtoken")
    public Mono<TokenDTO> getRefreshToken(@RequestBody TokenDTO token) {
        try {
            return service.refreshAccessToken(token.getRefresh_token());
        } catch (URISyntaxException | IOException | GeneralSecurityException e) {
            e.printStackTrace();
            return null;//ResponseEntity.badRequest().build();
        }
    }
}
