package com.zcore.mabokeserver.google.gfile;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.drive.model.File;
import com.zcore.mabokeserver.common.mapper.dto.FileDTO;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/gfile")
public class GFileController {
    @Autowired
    private GFileService service;

    @GetMapping(value = "/content")
    public Mono<Object> getDrive(@RequestParam String id) {
        return service.getDriveFileContent(id);
    }

    @GetMapping(value = "/content2")
    public Mono<Object> getDrive2(@RequestParam String id) {
        return service.getDriveFileContent2(id);
    }
    
    @GetMapping(value = "/name")
    public Mono<Map<String, String>> getFileByName(@RequestHeader String token,  @RequestParam String fileName) {
        return this.service.getDriveFileByName(token, fileName);
    }

    @PostMapping(value = "/create")
    public Mono<File> createFile(@RequestHeader(value="token")String token, @RequestBody FileDTO dto) {
        return this.service.createFile(token, dto.getFoldersPaths(), dto.getFileName(), dto.getMimeType(), dto.getFileContent());
    }

    @PostMapping(value = "/append")
    public Mono<File> appendFileToFolder(@RequestHeader(value="token")String token, @RequestBody FileDTO dto) {
        return this.service.appendFile(token, dto.getParentFileId(), dto.getFileName(), dto.getMimeType(), dto.getFileContent());
    }

    @PostMapping(value = "/folder")
    public Mono<File> createFolder(@RequestHeader(value="token")String token, @RequestBody FileDTO dto) {
        return this.service.createFolder(token, dto.getFolderName());
    }

    @GetMapping(value = "/download")
    public ResponseEntity<InputStreamResource> downFile(@RequestHeader(value="token")String token, @RequestParam String fileId, @RequestParam String mimeType) {
        return this.service.downFile(token, fileId, mimeType);
    }

    @PutMapping
    public Mono<File> updateFile(@RequestHeader(value="token")String token, @RequestBody FileDTO dto) {
        return this.service.updateFile(token, dto.getFileId(), dto.getFileName(), dto.getMimeType(), dto.getFileContent());
    }
    
    @DeleteMapping(value = "/delete")
    public Mono<String> deleFile(@RequestHeader(value="token")String token, @RequestParam String fileId) {
        return this.service.deleFile(token, fileId);
    }
}
