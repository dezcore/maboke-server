package com.zcore.mabokeserver.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

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
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.zcore.mabokeserver.common.exception.InvalidTokenException;
import com.zcore.mabokeserver.studiomaker.mapper.dto.TokenDTO;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

@RequiredArgsConstructor
@Service
public class DriveService {
    @Value("${api.google.api.url}")
    private String googleApiUrl;

    @Value("${api.google.auth.uri}")
    private String googleApiAuthUri;

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
            logger.info("Files : ");
            for(File file : files) {
                logger.info("%s (%s)\n", file.getName(), file.getId());
            }
        }
    }

    public Drive getService(String token) throws GeneralSecurityException, IOException {
        Drive service = null;
        NetHttpTransport HTTP_TRANSPORT;
        GoogleCredentials credentials;

        if(token != null) {
            credentials = GoogleCredentials.newBuilder().setAccessToken(new AccessToken(token, null)).build();
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            service = new Drive.Builder(HTTP_TRANSPORT, GsonFactory.getDefaultInstance(), new HttpCredentialsAdapter(credentials))
                .setApplicationName("Maboke/1.0")
                .build();
        }

        return service;
    }

    public Mono<FileList> getDriveFiles(String token) {

        return Mono.just(token)
        .map(token_ -> {
          try {
            FileList result;
            Drive service = getService(token_);

            if(service != null) {
                result = service.files().list()
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

    public File initFile(String fileName, String fileContent) {
        File file = new File();

        if(fileName != null) {
            file.setName(fileName);
            //file.setMimeType("");
            //file.setParents(null);
            //file.setDescription(description);
            //file.setPermissions(null)
        }

        return file;
    }

    public Mono<File> createFile(String token, String fileName, String fileContent) {
        return Mono.just(token)
        .map(token_ -> {
          try {
            File result = null;
            Drive service = getService(token_);
            File fileMetadata = initFile(fileName, fileContent);
            java.io.File filePath = new java.io.File("files/photo.jpg");
            FileContent mediaContent = new FileContent("image/jpeg", filePath);

            //InputStream stream = new ByteArrayInputStream(fileContent.getBytes(Charset.forName("UTF-8")));

            if(service != null && fileMetadata != null) {
                result = service.files().create(fileMetadata, mediaContent)
                .execute();
                return result;
            }

          } catch (IOException | GeneralSecurityException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, token_);
          }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, token_);
        });
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
          try {
            TokenDTO tokenDTO = null;

            GoogleAuthorizationCodeTokenRequest request = new GoogleAuthorizationCodeTokenRequest(
                new NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                CLIENT_ID.trim(),
                CLIENT_SECRET.trim(),
                code_.trim(),
                "postmessage"
            );

            GoogleTokenResponse token = request.execute();

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
