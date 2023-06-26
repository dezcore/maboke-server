package com.zcore.mabokeserver.google.gpermission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.drive.model.Permission;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/gpermission")
public class GPermissionController {
    @Autowired
    private GPermissionService service;
    
    @PostMapping(value = "/file")
    public Mono<Permission> createPermission(@RequestHeader(value="token")String token, @RequestBody GPermission permission) {
        return this.service.setFilePermission(token, permission.getFileId(), permission.getType(), permission.getRole());
    }
}
