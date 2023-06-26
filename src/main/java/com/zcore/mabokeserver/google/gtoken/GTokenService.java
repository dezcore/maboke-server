package com.zcore.mabokeserver.google.gtoken;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.zcore.mabokeserver.common.exception.InvalidTokenException;
import com.zcore.mabokeserver.common.mapper.dto.TokenDTO;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class GTokenService {
  @Value("${api.google.api.url}")
  private String googleApiUrl;

  @Value("${api.google.auth.uri}")
  private String googleApiAuthUri;

  @Value("${api.google.clienid}")
  private String CLIENT_ID;
  
  @Value("${api.google.codesecret}")
  private String CLIENT_SECRET;

  private Logger logger = LoggerFactory.getLogger(GTokenService.class);

  public Mono<TokenDTO> postRequest(MultiValueMap<String, String> bodyValues, String url, String uri) {
    Mono<TokenDTO> response = null;

    response = WebClient.create(url).post()
      .uri(uri)
      .accept(MediaType.APPLICATION_JSON)
      .body(BodyInserters.fromFormData(bodyValues))
      .retrieve()
      .bodyToMono(TokenDTO.class)
      .doOnSuccess(res -> {
        logger.info(res.toString());

          if(res.getAccess_token() == null)
            throw new InvalidTokenException("Request wasn't successfully validated");

      }).doOnError(e -> {
        logger.error("error verify captcha : {}", e.getMessage());
        throw new InvalidTokenException(e.getMessage());
    });
    return response;
  }

  public Mono<TokenDTO> getAccessToken(String code) {
    Mono<TokenDTO> response = null;
    MultiValueMap<String, String> bodyValues = new LinkedMultiValueMap<>();
    bodyValues.add("client_id", CLIENT_ID);
    bodyValues.add("client_secret", CLIENT_SECRET);
    bodyValues.add("code", code);
    bodyValues.add("grant_type", "authorization_code");
    bodyValues.add("redirect_uri", "postmessage");
    response = postRequest(bodyValues, googleApiUrl, googleApiAuthUri);

    return response;
  }

  public  Mono<TokenDTO> getAccessTokenByLib(String code) {
    return Mono.just(code)
      .map(code_ -> {
        TokenDTO tokenDTO;
        GoogleTokenResponse token;
        GoogleAuthorizationCodeTokenRequest request;
        try {
          tokenDTO = null;

          request = new GoogleAuthorizationCodeTokenRequest(
            new NetHttpTransport(),
            GsonFactory.getDefaultInstance(),
            CLIENT_ID.trim(),
            CLIENT_SECRET.trim(),
            code_.trim(),
            "postmessage"
          );

          token = request.execute();

          if(token.getAccessToken() != null) {
            tokenDTO = new TokenDTO();
            tokenDTO.setAccess_token(token.getAccessToken());
            tokenDTO.setExpires_in(token.getExpiresInSeconds());
            tokenDTO.setRefresh_token(tokenDTO.getRefresh_token());

            return tokenDTO;
          }
        } catch (IOException e) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, code_);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, code_);
    });
  }

  public Mono<TokenDTO> refreshAccessToken(String refresh_token) {
    Mono<TokenDTO> response = null;
    MultiValueMap<String, String> bodyValues = new LinkedMultiValueMap<>();

    bodyValues.add("client_id", CLIENT_ID);
    bodyValues.add("client_secret", CLIENT_SECRET);
    bodyValues.add("refresh_token", refresh_token);
    bodyValues.add("grant_type", "refresh_token");
    response = postRequest(bodyValues, googleApiUrl, googleApiAuthUri);

    return response;
  }
}
