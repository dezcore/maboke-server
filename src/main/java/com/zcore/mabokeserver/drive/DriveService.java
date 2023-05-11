package com.zcore.mabokeserver.drive;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils.Null;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.zcore.mabokeserver.studiomaker.mapper.dto.TokenDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import com.google.api.services.drive.Drive;
//import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

@Slf4j
@RequiredArgsConstructor
@Service
public class DriveService {    
    @Value("${api.google.clienid}")
    private String CLIENT_ID;

    @Value("${api.google.codesecret}")
    private String CLIENT_SECRET;

    private final WebClient.Builder webclientBuilder;


    private Logger logger = LoggerFactory.getLogger(DriveService.class);

    private static final String DRIVE_ROOT_URI = "https://www.googleapis.com/drive/v3";
    private static final String TOKEN_URI = "https://accounts.google.com/o/oauth2/token";

    public void displayFiles(FileList result) {
        List<File> files = result.getFiles();
        if(files == null || files.isEmpty()) {
            logger.info("No files found.");
        } else {
            logger.info("Files:");
            for(File file : files) {
                logger.info("%s (%s)\n", file.getName(), file.getId());
            }
        }
    }

    public void getDriveFiles(GoogleTokenResponse token) throws IOException, GeneralSecurityException {
        GoogleCredentials credentials = GoogleCredentials.newBuilder().setAccessToken(new AccessToken(token.getAccessToken(), null)).build();
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, GsonFactory.getDefaultInstance(), new HttpCredentialsAdapter(credentials))
        .setApplicationName("Maboke/1.0")
        .build();

        FileList result = service.files().list()
        .setPageSize(10)
        .setFields("nextPageToken, files(id, name)")
        .execute();
        displayFiles(result);
    }
    
    public String test_token(String code) throws IOException, GeneralSecurityException {
        GoogleAuthorizationCodeTokenRequest request =
        new GoogleAuthorizationCodeTokenRequest(
            new NetHttpTransport(),
            GsonFactory.getDefaultInstance(),
            CLIENT_ID.trim(),
            CLIENT_SECRET.trim(),
            code.trim(),
            "postmessage");

        GoogleTokenResponse token = request.execute();
        logger.info("token : " + token.getAccessToken());
        getDriveFiles(token);

        return token.getAccessToken();
    }

    public Mono<String> testGet() {
        log.info("testGet");
        Mono<String> response = WebClient.create("https://httpbin.org")
            .get()
            .uri("/get")
            .retrieve()
            .bodyToMono(String.class)
            .doOnSuccess(res -> {
                logger.info(res.toString());
            }).doOnError(e -> {
                logger.error("error testGet : {}", e.getMessage());
                //throw new InvalidCaptchaException(e.getMessage());
            });

           return response;
    }

    public Mono<String> getAccessToken(String code, String scope) throws URISyntaxException, IOException, GeneralSecurityException {
        return testGet();
       // String token = test_token(code);
        //return ResponseEntity.ok().body(token);
        //return fetchToken(code, scope);
        
        /*Mono<TokenDTO> response = null;
        MultiValueMap<String, String> bodyValues = new LinkedMultiValueMap<>();
        String clientCredentials = Base64.getEncoder().encodeToString((CLIENT_ID+":"+CLIENT_SECRET).getBytes());

        bodyValues.add("code", code);
        bodyValues.add("grant_type", "authorization_code");
        //bodyValues.add("redirect_uri", "http://localhost:8080/oauth2/callback/google");
        bodyValues.add("scope", scope);

        response = webclientBuilder.build().post()
        .uri(new URI(TOKEN_URI))
        .header("Authorization", "Basic "+ clientCredentials)
        .accept(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromFormData(bodyValues))
        .retrieve()
        .bodyToMono(TokenDTO.class)
        .doOnSuccess(res -> {
            logger.info(res.toString());
        }).doOnError(e -> {
            logger.error("error verify captcha : {}", e.getMessage());
            //throw new InvalidCaptchaException(e.getMessage());
        });

        return response;*/
        return null;
    }

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
