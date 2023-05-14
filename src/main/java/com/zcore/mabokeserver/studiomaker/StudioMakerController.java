package com.zcore.mabokeserver.studiomaker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.drive.model.FileList;
import com.zcore.mabokeserver.common.mapper.dto.TokenDTO;

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
    public  Mono<FileList> getFiles(@RequestHeader(value="tokens")TokenDTO token) {
        return service.getFiles(token.getAccess_token());
    }
}
