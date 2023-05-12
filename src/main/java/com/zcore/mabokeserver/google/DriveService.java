package com.zcore.mabokeserver.google;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.zcore.mabokeserver.studiomaker.mapper.dto.TokenDTO;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

@RequiredArgsConstructor
@Service
public class DriveService {    
    @Value("${api.google.clienid}")
    private String CLIENT_ID;

    @Value("${api.google.codesecret}")
    private String CLIENT_SECRET;
    
    private Logger logger = LoggerFactory.getLogger(DriveService.class);

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

    public Mono<String> getFiles(String url, String uri, String access_token) {
        //String url =  "https://www.googleapis.com";
        //String uri = "/drive/v3/files";
        Mono<String> response = WebClient.create(url)
            .get()
            .uri(uri)
            //.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED)
            //.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            //.header("Authorization",  "Bearer " + access_token.trim())
            .retrieve()
            .bodyToMono(String.class)
            .doOnSuccess(res -> {
                logger.info(res.toString());
            }).doOnError(e -> {
                logger.error("error testGet : {}", e.getMessage());
                //throw new InvalidCaptchaException(e.getMessage());
            });
            //Gson gson = new Gson();
            //DriveFiles driveFiles = gson.fromJson(response.getBody(), DriveFiles.class);
           return response;
    }

    public Mono<TokenDTO> postRequest(MultiValueMap<String, String> bodyValues, String url, String uri) throws URISyntaxException, IOException, GeneralSecurityException {
        Mono<TokenDTO> response = null;

        response = WebClient.create(url).post()
        .uri(uri)
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

        return response;
    }

    public Mono<TokenDTO> getAccessToken(String code) throws URISyntaxException, IOException, GeneralSecurityException {
        Mono<TokenDTO> response = null;
        MultiValueMap<String, String> bodyValues = new LinkedMultiValueMap<>();

        bodyValues.add("client_id", CLIENT_ID);
        bodyValues.add("client_secret", CLIENT_SECRET);
        bodyValues.add("code", code);
        bodyValues.add("grant_type", "authorization_code");
        bodyValues.add("redirect_uri", "postmessage");
        response = postRequest(bodyValues, "https://www.googleapis.com", "/oauth2/v4/token");

        return response;
    }

    public String getAccessToken_bylib(String code) throws IOException, GeneralSecurityException {
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

    public Mono<TokenDTO> refreshAccessToken(String refresh_token) throws URISyntaxException, IOException, GeneralSecurityException {
        Mono<TokenDTO> response = null;
        MultiValueMap<String, String> bodyValues = new LinkedMultiValueMap<>();

        bodyValues.add("client_id", CLIENT_ID);
        bodyValues.add("client_secret", CLIENT_SECRET);
        bodyValues.add("refresh_token", refresh_token);
        bodyValues.add("grant_type", "refresh_token");
        response = postRequest(bodyValues, "https://www.googleapis.com", "/oauth2/v4/token");

        return response;
    }
}
