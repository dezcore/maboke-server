package com.zcore.mabokeserver.studiomaker;


import com.zcore.mabokeserver.drive.DriveService;

import java.net.URISyntaxException;

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

    public ResponseEntity<String> getToken(String code, String scope) {
        try {
            return service.getAccessToken(code, scope);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<String> getFiles(String accessToken) {
        return service.getDriveFiles(accessToken);
    }
}
