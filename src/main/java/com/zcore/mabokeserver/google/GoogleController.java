package com.zcore.mabokeserver.google;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.zcore.mabokeserver.common.mapper.dto.FileDTO;
import com.zcore.mabokeserver.common.mapper.dto.TokenDTO;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/google")
public class GoogleController {
    @Autowired
    private DriveService service;

    @GetMapping(value = "/token")
    public Mono<TokenDTO> getToken(@RequestBody TokenDTO token) {
        return service.getAccessToken(token.getCode());
    }

    @GetMapping(value = "/tokenbylib")
    public Mono<TokenDTO> getTokenBylib(@RequestBody TokenDTO token) {
        return service.getAccessTokenByLib(token.getCode());
    }

    @GetMapping(value = "/refreshtoken")
    public Mono<TokenDTO> getRefreshToken(@RequestBody TokenDTO token) {
        return service.refreshAccessToken(token.getRefresh_token());
    }

    @GetMapping(value = "/drive/files")
    public  Mono<FileList> getFiles(@RequestHeader(value="token")String token) {
        return service.getDriveFiles(token);
    }

    @GetMapping(value = "/drive/files/download")
    public ResponseEntity<InputStreamResource> downFile(@RequestHeader(value="token")String token, @RequestParam String fileId,@RequestParam String mimeType) {
        return service.downFile(token, fileId, mimeType);
    }

    @PostMapping(value = "/drive/files/create")
    public Mono<File> createFile(@RequestHeader(value="token")String token, @RequestBody FileDTO dto) {
        return service.createFile(token, dto.getFoldersPaths(), dto.getFileName(), dto.getMimeType(), dto.getFileContent());
    }

    @PostMapping(value = "/drive/folders/create")
    public Mono<File> createFolder(@RequestHeader(value="token")String token, @RequestBody FileDTO dto) {
        return service.createFolder(token, dto.getFolderName());
    }

    @PostMapping(value = "/drive/folders/paths")
    public List<String> createFolders(@RequestHeader(value="token")String token, @RequestBody FileDTO dto) {
        return service.createFolders(token, dto.getFoldersPaths());
    }

    @DeleteMapping(value = "/drive/files/delete")
    public Mono<String> deleFile(@RequestHeader(value="token")String token, @RequestParam String fileId) {
        return service.deleFile(token, fileId);
    }
}
