package com.zcore.mabokeserver.studiomaker;


import com.zcore.mabokeserver.drive.DriveService;
import com.zcore.mabokeserver.studiomaker.mapper.dto.TokenDTO;

import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StudioMakerService {
    @Autowired
    private DriveService service;
    private Logger logger = LoggerFactory.getLogger(StudioMakerService.class);
    
    public Mono<String> getTest() {
        return service.testGet();
    }

    public Mono<TokenDTO> getToken(String code, String scope) {
        try {
            return service.getAccessToken(code, scope);
        } catch (URISyntaxException | IOException | GeneralSecurityException e) {
            e.printStackTrace();
            return null;//ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<String> getFiles(String accessToken) {
        return service.getDriveFiles(accessToken);
    }
}
