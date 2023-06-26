package com.zcore.mabokeserver.google.gtoken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zcore.mabokeserver.common.mapper.dto.TokenDTO;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/gtoken")
public class GTokenController {
    @Autowired
    private GTokenService service;

    @PostMapping
    public Mono<TokenDTO> getToken(@RequestBody TokenDTO token) {
        return this.service.getAccessToken(token.getCode());
    }

    @GetMapping(value = "/lib")
    public Mono<TokenDTO> getTokenBylib(@RequestBody TokenDTO token) {
        return this.service.getAccessTokenByLib(token.getCode());
    }

    @GetMapping(value = "/refreshtoken")
    public Mono<TokenDTO> getRefreshToken(@RequestBody TokenDTO token) {
        return this.service.refreshAccessToken(token.getRefresh_token());
    }
}
