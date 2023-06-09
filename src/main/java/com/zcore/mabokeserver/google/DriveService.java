package com.zcore.mabokeserver.google;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.zcore.mabokeserver.common.exception.InvalidTokenException;
import com.zcore.mabokeserver.common.file.FileCommon;
import com.zcore.mabokeserver.common.mapper.dto.TokenDTO;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;

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

    /*public Mono<FileList> getDriveFiles(String token) {

        return Mono.just(token)
        .map(token_ -> {
          try {
            FileList result;
            Drive service = getService(token_);

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
    }*/

    /*public Mono<java.util.Map<String, String>> getDriveFilesByName(String token, String fileName) {
        return Mono.just(token)
        .map(token_ -> {
          try {
            FileList result;
            Drive service = getService(token_);
            java.util.Map<String, String> map = new HashMap<>();
            //logger.info("getDriveFilesByName : " + fileName + ", " + token_);
            if(service != null) {
                result = service.files().list()
                    //.setSpaces("appDataFolder")
                    //.setPageSize(10)
                    .setFields("nextPageToken, files(id, name)")
                    .execute();
                    
                for(File file : result.getFiles()) {
                  if(file.getName().contains(fileName)) {
                    map.put(file.getName(), file.getId());
                  }
                }
                return map;
            }
          } catch (IOException | GeneralSecurityException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, token_);
          }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, token_);
        });
    }*/

  /*public File initFile(String token, String foldersPaths, String fileName) {
    List<String> parents = null;
    File file = new File();

    if(foldersPaths != null)
      parents = createFolders(token, foldersPaths);

    if(fileName != null) {
      //file.setName("config.json");
      file.setName(fileName);
      //file.setMimeType("");
      if(parents != null)
        file.setParents(parents);
      //file.setParents(Collections.singletonList(realFolderId));
      //file.setDescription(description);
    }

    return file;
  }*/

  /*public boolean containsFile(FileList files) {
    if(files != null) {
      for(File file : files.getFiles()) {
        System.out.printf("Found file: %s (%s)\n", file.getName(), file.getId());
      }
    }

    return false;
  }*/

  /*public Mono<List<String>> getFileParents(String token, String fileId) {
    return Mono.just(token)
      .map(token_ -> {
        try {
          File file;
          Drive service = getService(token_);      

          if(service != null) {
            file = service.files().get(fileId)
            .setFields("parents")
            .execute();
            return file.getParents();
          }

        } catch (IOException | GeneralSecurityException e) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, token_);
    });
  }*/

  /*public Mono<Permission> setFilePermission(String token, String fileId, String type, String role) {
    return Mono.just(token)
      .map(token_ -> {
        Permission create;
        Permission permission = new Permission();
        try {
          Drive service = getService(token_);
          permission.setType(type);
          permission.setRole(role);
          create = service.permissions().create(fileId, permission).setFields("id").execute();
          return create;
        } catch (IOException | GeneralSecurityException e) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
      });
  }*/
  
    /*public Mono<File> createFolder(String token, String folderName) {
        return Mono.just(token)
        .map(token_ -> {
          try {
            File file;
            File fileMetadata = new File();
            Drive service = getService(token_);

            fileMetadata.setName(folderName);
            fileMetadata.setMimeType("application/vnd.google-apps.folder");
            

            if(service != null) {
                file = service.files().create(fileMetadata)
                .setFields("id")
                .execute();
                return file;
            }

          } catch (IOException | GeneralSecurityException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
          }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, token_);
        });
    }*/

    /*public List<String> createFolders(String token, String foldersPath) {
      Mono<File> res;
      String[] folders;
      List<String> parents = new ArrayList<String>();

      if(foldersPath != null) {  
        folders = foldersPath.split("/");

        for(String folder : folders) {
          res = createFolder(token, folder);
          res.doOnNext(result ->  parents.add(result.getId())).subscribe();
        }
      }

      return parents;
    }*/

  /*public Mono<File> createFile(String token, String foldersPaths, String fileName, String mimeType, JsonNode fileContent) {
    return Mono.just(token)
      .map(token_ -> {
        try {
          File result = null;
          Drive service = getService(token_);
          File fileMetadata = initFile(token, foldersPaths, fileName);
          java.io.File filePath = FileCommon.writeTmpFile(fileName, String.valueOf(fileContent));
          FileContent mediaContent = new FileContent(mimeType, filePath);//"application/json" //"text/plain"

          if(service != null && fileMetadata != null) {
            result = service.files().create(fileMetadata, mediaContent)
            .setFields("id, parents")
            .execute();
            return result;
          }
        } catch (IOException | GeneralSecurityException e) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, token_);
    });
  }*/

    /*public Mono<File> updateFile(String token, String fileId, String fileName, String mimeType, JsonNode fileContent) {
        return Mono.just(token)
        .map(token_ -> {
          try {
            File result = null;
            Drive service = getService(token_);
            java.io.File filePath = FileCommon.writeTmpFile(fileName, String.valueOf(fileContent));
            FileContent mediaContent = new FileContent(mimeType, filePath);
            
            if(service != null) {
                result = service.files().update(fileId, null, mediaContent)
                .setFields("id, parents")
                .execute();
                return result;
            }
          } catch (IOException | GeneralSecurityException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
          }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, token_);
        });
    }*/

    /*public File setFileParents(String token, String parentFileId, String fileName) {
      File file = new File();
      List<String> parentsIds = new ArrayList<>();

      if(fileName != null) {
        file.setName(fileName);
        parentsIds.add(parentFileId);
        file.setParents(parentsIds);
      }

      return file;
    }*/
    /*public Mono<File> appendFile(String token, String parentFileId, String fileName, String mimeType, JsonNode fileContent) {
        return Mono.just(token)
        .map(token_ -> {
          try {
            File result = null;
            Drive service = getService(token_);
            File fileMetadata = setFileParents(token, parentFileId, fileName);
            java.io.File filePath = FileCommon.writeTmpFile(fileName, String.valueOf(fileContent));
            FileContent mediaContent = new FileContent(mimeType, filePath);//"application/json" //"text/plain"
           
            if(service != null && fileMetadata != null) {
                result = service.files().create(fileMetadata, mediaContent)
                .setFields("id, parents")
                .execute();
                return result;
            }

          } catch (IOException | GeneralSecurityException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
          }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, token_);
        });
    }*/

    /*public ResponseEntity<InputStreamResource> downFile(String token, String fileId,  String mimeType) {
      Drive service;
      java.io.File file;
      MediaType contentType;
      FileInputStream input;
      OutputStream outputStream;
      InputStreamResource resource;
      String filePath = "/tmp/" + fileId;

      try {
        service = getService(token);

        if(service != null) {
          outputStream = new FileOutputStream(filePath);
          service.files().get(fileId)
          .executeMediaAndDownloadTo(outputStream);

          file = new java.io.File(filePath);
          input = new FileInputStream(file);
          contentType =   MediaType.parseMediaType(mimeType);
          resource = new InputStreamResource(input);

          return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
          .contentType(contentType)
          .contentLength(file.length())
          .body(resource);
         
        }

      } catch (IOException | GeneralSecurityException e) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
      } 

      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, fileId);
    }*/

    /*public Mono<ByteArrayOutputStream> exporFile(String token, String fileId, String mimeType) {
        return Mono.just(token)
        .map(token_ -> {
          try {
            
            OutputStream outputStream = new ByteArrayOutputStream();
            Drive service = getService(token_);
            
            if(service != null) {
                service.files().export(fileId, mimeType)//"application/pdf"
                    .executeMediaAndDownloadTo(outputStream);
                
                    return(ByteArrayOutputStream) outputStream;
            }

          } catch (IOException | GeneralSecurityException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
          }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, token_);
        });
    }*/

    /*public Mono<String> deleFile(String token, String fileId) {
        return Mono.just(token)
        .map(token_ -> {
          try {
            Drive service = getService(token_);
            
            if(service != null) {
              service.files().delete(fileId).execute();
              return fileId;
            }

          } catch (IOException | GeneralSecurityException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
          }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, token_);
        });
    }*/

    /*public Mono<TokenDTO> postRequest(MultiValueMap<String, String> bodyValues, String url, String uri) {
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
    }*/

    /*public Mono<TokenDTO> getAccessToken(String code) {
        Mono<TokenDTO> response = null;
        MultiValueMap<String, String> bodyValues = new LinkedMultiValueMap<>();
        bodyValues.add("client_id", CLIENT_ID);
        bodyValues.add("client_secret", CLIENT_SECRET);
        bodyValues.add("code", code);
        bodyValues.add("grant_type", "authorization_code");
        bodyValues.add("redirect_uri", "postmessage");
        response = postRequest(bodyValues, googleApiUrl, googleApiAuthUri);

        return response;
    }*/
    
    /*public  Mono<TokenDTO> getAccessTokenByLib(String code) {
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
    }*/

    /*public Mono<TokenDTO> refreshAccessToken(String refresh_token) {
      Mono<TokenDTO> response = null;
      MultiValueMap<String, String> bodyValues = new LinkedMultiValueMap<>();

      bodyValues.add("client_id", CLIENT_ID);
      bodyValues.add("client_secret", CLIENT_SECRET);
      bodyValues.add("refresh_token", refresh_token);
      bodyValues.add("grant_type", "refresh_token");
      response = postRequest(bodyValues, googleApiUrl, googleApiAuthUri);

      return response;
    }*/

    /*public void jsonObjHandler(JsonObject obj,  List<String> res) {
      //JsonElement current;
      if(obj != null) {
        res.add(obj.get("s").getAsString());
      }
    }*/

    /*public void jsonArrayHandler(JsonArray array,  List<String> res) {
      JsonObject jsonObj;
      JsonElement current, targetElement;

      if(array != null) {
        for(int i = 0; i < array.size(); i++) {
          current = array.get(i);
          if(current != null && current.isJsonObject()) {
            jsonObj = current.getAsJsonObject();
            targetElement = jsonObj.get("s");
            if(targetElement != null) {
              res.add(targetElement.getAsString());
            }
          }
        }
      }
    }*/

    /*public void extractJson(String json,  List<String> res) {
      JsonElement element;

      try{
        JsonReader reader = new JsonReader(new StringReader(json));
        reader.setLenient(true);
        element = JsonParser.parseReader(reader);

        if(element.isJsonArray()) {
          jsonArrayHandler(element.getAsJsonArray(), res);
        } else if(element.isJsonObject()) {
          jsonObjHandler(element.getAsJsonObject(), res);
        }
      } catch (JsonParseException e) {
        e.printStackTrace();
      }
    }*/

    /*public void extractTextContent(String content,  List<String> res) {
      Pattern pattern = Pattern.compile("\\[.*\\]");
      Matcher matcher = pattern.matcher(content);

      if(matcher.find()) {
        for(int i=0; i <= matcher.groupCount(); i++) {
          extractJson(matcher.group(i), res);
        }
      }
    }*/

    /*public void parseHtml(String html, List<String> res) {
      Document doc = Jsoup.parse(html);
      Elements elements = doc.select("script");

      for(Element element : elements) {
        if(element.data().contains("DOCS_modelChunk =")) {
          extractTextContent(element.data(), res);
        }
      }
    }*/

    /*public Mono<Object> getDriveFileContent(String id) {
      List<String> res = new ArrayList<String>();

      return WebClient.create("https://docs.google.com")
      .get()
      .uri("/document/d/" + id + "/edit?usp=sharing")
      .retrieve()
      .bodyToMono(byte[].class).map(result -> {
        String html = new String(result, StandardCharsets.UTF_8);
        parseHtml(html, res);
        return String.join("\n", res);
    });
  }*/
}
