package com.zcore.mabokeserver.google.gpermission;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.Permission;
import com.zcore.mabokeserver.google.gclient.GClientService;

@RequiredArgsConstructor
@Service
public class GPermissionService {
  @Autowired
  private GClientService clientService;

  private Logger logger = LoggerFactory.getLogger(GPermissionService.class);
  
  public Mono<Permission> setFilePermission(String token, String fileId, String type, String role) {
     logger.info("fileId : " + fileId);
    return Mono.just(token)
      .map(token_ -> {
        Permission create;
        Permission permission = new Permission();
        try {
          logger.info("fileId : " + fileId);
          Drive service = this.clientService.getService(token_);
          permission.setType(type);
          permission.setRole(role);
          create = service.permissions().create(fileId, permission).setFields("id").execute();
          return create;
        } catch (IOException | GeneralSecurityException e) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    });
  }
}
