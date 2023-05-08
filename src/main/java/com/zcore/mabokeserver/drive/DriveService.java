package com.zcore.mabokeserver.drive;


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class DriveService {    
    @Value("${api.google.clienid}")
    private String CLIENT_ID;

    @Value("${api.google.codesecret}")
    private String CLIENT_SECRET;

    private final WebClient webClient;
    private final String clientCredentials;

    private Logger logger = LoggerFactory.getLogger(DriveService.class);

    private static final String DRIVE_ROOT_URI = "https://www.googleapis.com/drive/v3";
    private static final String TOKEN_URI = "https://accounts.google.com/o/oauth2/token";
    
    public DriveService() {
		this.webClient = WebClient.create();
        this.clientCredentials = Base64.getEncoder().encodeToString((CLIENT_ID+":"+CLIENT_SECRET).getBytes());
	}

    public ResponseEntity<String> getAccessToken(String code, String scope) throws URISyntaxException {
        logger.info("getAccessToken : " + code);
        String response = null;
        MultiValueMap<String, String> bodyValues = new LinkedMultiValueMap<>();

        bodyValues.add("code", code);
        bodyValues.add("grant_type", "authorization_code");
        //bodyValues.add("redirect_uri", "http://localhost:8080/oauth2/callback/google");
        bodyValues.add("scope", scope);

        response = this.webClient.post()
        .uri(new URI(TOKEN_URI))
        .header("Authorization", "Basic "+ this.clientCredentials)
        .accept(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromFormData(bodyValues))
        .retrieve()
        .bodyToMono(String.class)
        .block();

        logger.info("Response : " + response);

        return ResponseEntity.ok(response);
    }


    /*public ResponseEntity<String> fetchToken(String code, String scope) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String clientCredentials = Base64.getEncoder().encodeToString((CLIENT_ID+":"+CLIENT_SECRET).getBytes());
        headers.add("Authorization", "Basic "+clientCredentials);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("code", code);
        requestBody.add("grant_type", "authorization_code");
        //requestBody.add("redirect_uri", "http://localhost:8080/oauth2/callback/google");
        requestBody.add("scope", scope);

        HttpEntity formEntity = new HttpEntity<MultiValueMap<String, String>>(requestBody, headers);
        ResponseEntity<Object> response = restTemplate.exchange(TOKEN_URI, HttpMethod.POST, formEntity, Object.class OauthResponse.class);
        //logger.info("Token : " + response.getBody());
        //return response.getBody().getAccess_token();
        if(response != null && response.getBody() != null){             
            logger.info("YES");
            logger.info(response.getBody().toString());
        } else {
            logger.info("NONES");
        }
        return null;//response;
    }*/
    
    public ResponseEntity<String> getDriveFiles(String accessToken) {        
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + accessToken.trim());

        HttpEntity request = new HttpEntity(headers);
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
        ResponseEntity<String> response = restTemplate.exchange(DRIVE_ROOT_URI + "/files", HttpMethod.GET, request, String.class);
        //Gson gson = new Gson();
        //DriveFiles driveFiles = gson.fromJson(response.getBody(), DriveFiles.class);
        //return driveFiles;
        return response;
    }
}
