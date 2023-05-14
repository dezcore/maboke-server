package com.zcore.mabokeserver.studiomaker;


import com.google.api.services.drive.model.FileList;
import com.zcore.mabokeserver.google.DriveService;

import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudioMakerService {
    @Autowired
    private DriveService service;
    private Logger logger = LoggerFactory.getLogger(StudioMakerService.class);

    public Mono<FileList>  getFiles(String token) {
        return service.getDriveFiles(token);
    }
}
