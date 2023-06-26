package com.zcore.mabokeserver.google.gfiles;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.zcore.mabokeserver.common.mapper.dto.FileDTO;
import com.zcore.mabokeserver.google.gclient.GClientService;
import com.zcore.mabokeserver.google.gfile.GFileService;
import com.zcore.mabokeserver.google.gpermission.GPermissionService;

@RequiredArgsConstructor
@Service
public class GFilesService {
  @Autowired
  private GPermissionService pService;
  
  @Autowired
  private GFileService fileService;

  @Autowired
  private GClientService clientService;

  private Logger logger = LoggerFactory.getLogger(GFilesService.class);

  public void displayFiles(FileList result) {
    List<File> files = result.getFiles();
    if(files == null || files.isEmpty()) {
      logger.info("No files found.");
    } else {
      logger.info("Files : ");
      for(File file : files) {
        logger.info("%s (%s)\n", file.getName(), file.getId());
      }
    }
  }

  public boolean containsFile(FileList files) {
    if(files != null) {
      for(File file : files.getFiles()) {
        System.out.printf("Found file: %s (%s)\n", file.getName(), file.getId());
      }
    }

    return false;
  }
  
  public Mono<FileList> getDriveFiles(String token) {
    return Mono.just(token)
      .map(token_ -> {
        try {
          FileList result;
          Drive service = this.clientService.getService(token_);
          if(service != null) {
            result = service.files().list()
            //.setSpaces("appDataFolder")
            .setPageSize(10)
            .setFields("nextPageToken, files(id, name)")
            .execute();

            return result;
          }
        } catch (IOException | GeneralSecurityException e) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, token_);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, token_);
    });
  }

  public Mono<List<File>> createFiles(String token,  FileDTO dto) {
    return Mono.just(dto)
      .map(dto_ -> {
        int index = 0;
        Mono<File> file;        
        JsonNode fileContent;
        List<File> files = new ArrayList<>();
        String folderPath = dto_.getFoldersPaths();
        String mimeType = dto_.getMimeType();

        for(String name : dto_.getFilesNames()) {
          fileContent = dto_.getContents().get(index);
          file = this.fileService.createFile(token, folderPath, name, mimeType, fileContent);
          file.doOnNext(res -> {
            files.add(res);
          }).subscribe();
          index++;
        }
        return files;
    });
  }

  public Mono<List<File>> appendFiles(String token,  FileDTO dto) {
    return Mono.just(dto)
      .map(dto_ -> {
        int index = 0;
        Mono<File> file;        
        JsonNode fileContent;
        List<File> files = new ArrayList<>();
        String mimeType = dto_.getMimeType();

        for(String name : dto_.getFilesNames()) {
          fileContent = dto_.getContents().get(index);
          file = this.fileService.appendFile(token, dto_.getParentFileId(), name, mimeType, fileContent);
          file.doOnNext(res -> {
            this.pService.setFilePermission(token, res, dto.getPermission());
            files.add(res);
          }).subscribe();
          index++;
        }
        return files;
    });
  }

  public List<String> createFolders(String token, String foldersPath) {
    return this.fileService.createFolders(token, foldersPath);
  }
  
  public Mono<java.util.Map<String, String>> getDriveFilesByName(String token, List<String> names) {
    return this.fileService.getDriveFilesByName(token, names);
  }

  public Mono<List<Object>> getDriveFilesContents(FileDTO dto) {
    logger.info("Get Contents");
    return Mono.just(dto)
      .map(dto_ -> {
        int index = 0;
        Mono<Object> content;        
        List<Object> filesContents = new ArrayList<>();
        for(String fileId : dto_.getFilesIds()) {
          content = this.fileService.getDriveFileContent(fileId);
          content.doOnNext(res -> {
            filesContents.add(res);
          }).subscribe();
          index++;
        }

        return filesContents;
    });
  }
}
