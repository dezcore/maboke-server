package com.zcore.mabokeserver.google.gfiles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.zcore.mabokeserver.common.mapper.dto.FileDTO;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/gfiles")
public class GFilesController {
    @Autowired
    private GFilesService service;

    @GetMapping
    public Mono<FileList> getFiles(@RequestHeader(value="token")String token) {
        return this.service.getDriveFiles(token);
    }

    @GetMapping(value = "/contents")
    public Mono<List<Object>> getDrive(@RequestBody FileDTO dto) {
        return this.service.getDriveFilesContents(dto);
    }

    @PostMapping
    public  Mono<List<File>> createFiles(@RequestHeader(value="token")String token, @RequestBody FileDTO dto) {
        return this.service.createFiles(token, dto);
    }

    @PostMapping(value="/append")
    public  Mono<List<File>> appendFiles(@RequestHeader(value="token")String token, @RequestBody FileDTO dto) {
        return this.service.appendFiles(token, dto);
    }

    @PostMapping(value = "/folders")
    public List<String> createFolders(@RequestHeader(value="token")String token, @RequestBody FileDTO dto) {
        return this.service.createFolders(token, dto.getFoldersPaths());
    }
}
