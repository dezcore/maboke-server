package com.zcore.mabokeserver.google.gpermission;

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

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import com.zcore.mabokeserver.google.gclient.GClientService;

@RequiredArgsConstructor
@Service
public class GPermissionService {
  @Autowired
  private GClientService clientService;

  private Logger logger = LoggerFactory.getLogger(GPermissionService.class);

  public void setFilePermission(String token, File file, GPermission gPermission) {
    Permission permission = new Permission();
    try {
      Drive service = this.clientService.getService(token);
      permission.setType(gPermission.getType());
      permission.setRole(gPermission.getRole());
      service.permissions().create(file.getId(), permission).setFields("id").execute();
    } catch (IOException | GeneralSecurityException e) {
      e.printStackTrace();
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  public Mono<Permission> setFilePermission(String token, String fileId, String type, String role) {
    return Mono.just(token)
      .map(token_ -> {
        Permission create;
        Permission permission = new Permission();
        try {
          Drive service = this.clientService.getService(token_);
          permission.setType(type);
          permission.setRole(role);
          create = service.permissions().create(fileId, permission).setFields("id").execute();
          return create;
        } catch (IOException | GeneralSecurityException e) {
          e.printStackTrace();
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    });
  }
  
  public Mono<List<Permission>> setFilePermissions(String token,  List<String> filesIds, String type, String role) {
    return Mono.just(token)
      .map(token_ -> {
        int index = 0;
        Mono<Permission> pMono;
        List<Permission> permissions = new ArrayList<>();

        for(String fileId : filesIds) {
          pMono = this.setFilePermission(token, fileId, type, role);
          pMono.doOnNext(res -> {
            permissions.add(res);
          }).subscribe();
          index++;
        }
        return permissions;
    });
  }
}
