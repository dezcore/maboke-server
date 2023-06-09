package com.zcore.mabokeserver.google;

import java.util.List;
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
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;
import com.zcore.mabokeserver.common.mapper.dto.FileDTO;
import reactor.core.publisher.Mono;
@RestController
@RequestMapping("/gapi")
public class GoogleController {
    @Autowired
    private DriveService service;

    /*@PostMapping(value = "/token")
    public Mono<TokenDTO> getToken(@RequestBody TokenDTO token) {
        return service.getAccessToken(token.getCode());
    }*/

    /*@GetMapping(value = "/filecontent")
    public Mono<Object> getDrive(@RequestParam String id) {
        return service.getDriveFileContent(id);
    }*/

    /*@GetMapping(value = "/tokenbylib")
    public Mono<TokenDTO> getTokenBylib(@RequestBody TokenDTO token) {
        return this.service.getAccessTokenByLib(token.getCode());
    }*/

    /*@GetMapping(value = "/refreshtoken")
    public Mono<TokenDTO> getRefreshToken(@RequestBody TokenDTO token) {
        return this.service.refreshAccessToken(token.getRefresh_token());
    }*/

    /*@GetMapping(value = "/drive/files")
    public Mono<FileList> getFiles(@RequestHeader(value="token")String token) {
        return this.service.getDriveFiles(token);
    }*/

    /*@GetMapping(value = "/dapi/name")
    public Mono<Map<String, String>> getFileByName(@RequestHeader String token,  @RequestParam String fileName) {
        return this.service.getDriveFilesByName(token, fileName);
    }*/

    /*@GetMapping(value = "/drive/files/download")
    public ResponseEntity<InputStreamResource> downFile(@RequestHeader(value="token")String token, @RequestParam String fileId,@RequestParam String mimeType) {
        return this.service.downFile(token, fileId, mimeType);
    }*/
    
    /*@PostMapping(value = "/drive/files/create")
    public Mono<File> createFile(@RequestHeader(value="token")String token, @RequestBody FileDTO dto) {
        return this.service.createFile(token, dto.getFoldersPaths(), dto.getFileName(), dto.getMimeType(), dto.getFileContent());
    }*/

    /*@PostMapping(value = "/dapi/file/append")
    public Mono<File> appendFileToFolder(@RequestHeader(value="token")String token, @RequestBody FileDTO dto) {
        return this.service.appendFile(token, dto.getParentFileId(), dto.getFileName(), dto.getMimeType(), dto.getFileContent());
    }*/

    /*@PostMapping(value = "/drive/folders/create")
    public Mono<File> createFolder(@RequestHeader(value="token")String token, @RequestBody FileDTO dto) {
        return this.service.createFolder(token, dto.getFolderName());
    }*/

    /*@PostMapping(value = "/dapi/permission")
    public Mono<Permission> createPermission(@RequestHeader(value="token")String token, @RequestBody  GPermission permission) {
        return this.service.setFilePermission(token, permission.getFileId(), permission.getType(), permission.getRole());
    }*/

    /*@PostMapping(value = "/drive/folders/paths")
    public List<String> createFolders(@RequestHeader(value="token")String token, @RequestBody FileDTO dto) {
        return this.service.createFolders(token, dto.getFoldersPaths());
    }*/

    /*@PutMapping(value = "/dapi/file")
    public Mono<File> updateFile(@RequestHeader(value="token")String token, @RequestBody FileDTO dto) {
        return this.service.updateFile(token, dto.getFileId(), dto.getFileName(), dto.getMimeType(), dto.getFileContent());
    }*/
    /*@DeleteMapping(value = "/drive/files/delete")
    public Mono<String> deleFile(@RequestHeader(value="token")String token, @RequestParam String fileId) {
        return this.service.deleFile(token, fileId);
    }*/
}
