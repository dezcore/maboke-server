package com.zcore.mabokeserver.studiomaker;


import com.zcore.mabokeserver.google.DriveService;
import com.zcore.mabokeserver.studiomaker.mapper.dto.TokenDTO;

import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudioMakerService {
    @Autowired
    private DriveService service;
    private Logger logger = LoggerFactory.getLogger(StudioMakerService.class);


    public Mono<TokenDTO> getAccessToken(String code) {
        try {
            return service.getAccessToken(code);
        } catch (URISyntaxException | IOException | GeneralSecurityException e) {
            e.printStackTrace();
            return null;//ResponseEntity.badRequest().build();
        }
    }

    public Mono<String> getFiles(String tokens) {
        return service.getFiles("https://www.googleapis.com",  "/drive/v3/files", tokens);
    }

    /*public ResponseEntity<String> getDriveFiles(String accessToken) {
        return service.getDriveFiles(accessToken);
    }*/
}
