package com.zcore.mabokeserver.google.gfile;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.zcore.mabokeserver.common.file.FileCommon;
import com.zcore.mabokeserver.google.gclient.GClientService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
@RequiredArgsConstructor
@Service
public class GFileService {
  @Autowired
  private GClientService clientService;
  private Logger logger = LoggerFactory.getLogger(GFileService.class);
  
  public void jsonObjHandler(JsonObject obj,  List<String> res) {
    //JsonElement current;
    if(obj != null) {
      res.add(obj.get("s").getAsString());
    }
  }

  public void jsonArrayHandler(JsonArray array,  List<String> res) {
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
  }

  public void extractJson(String json,  List<String> res) {
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
  }

  public void extractTextContent(String content,  List<String> res) {
    Pattern pattern = Pattern.compile("\\[.*\\]");
    Matcher matcher = pattern.matcher(content);

    if(matcher.find()) {
      for(int i=0; i <= matcher.groupCount(); i++) {
        extractJson(matcher.group(i), res);
      }
    }
  }

  public void parseHtml(String html, List<String> res) {
    Document doc = Jsoup.parse(html);
    Elements elements = doc.select("script");

    for(Element element : elements) {
      if(element.data().contains("DOCS_modelChunk =")) {
        extractTextContent(element.data(), res);
      }
    }
  }

  public Mono<Object> getDriveFileContent(String id) {
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
  }


   public Mono<java.util.Map<String, String>> getDriveFilesByName(String token, List<String> names) {
    return Mono.just(token)
      .map(token_ -> {
        try {
          FileList result;
          Drive service = this.clientService.getService(token_);
          java.util.Map<String, String> map = new HashMap<>();
          //logger.info("getDriveFilesByName : " + fileName + ", " + token_);
          if(service != null) {
            result = service.files().list()
            //.setSpaces("appDataFolder")
            //.setPageSize(10)
            .setFields("nextPageToken, files(id, name)")
            .execute();
                    
            for(File file : result.getFiles()) {
              if(names.contains(file.getName())) {
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
  }

  public Mono<java.util.Map<String, String>> getDriveFileByName(String token, String fileName) {
    List<String> names = new ArrayList<>();
    names.add(fileName);
    return this.getDriveFilesByName(token, names);
  }

  public List<String> createFolders(String token, String foldersPath) {
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
  }

  public File initFile(String token, String foldersPaths, String fileName) {
    List<String> parents = null;
    File file = new File();

    if(foldersPaths != null)
      parents = this.createFolders(token, foldersPaths);

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
  }
  
  public Mono<File> createFile(String token, String foldersPaths, String fileName, String mimeType, JsonNode fileContent) {
    return Mono.just(token)
      .map(token_ -> {
        try {
          File result = null;
          Drive service =this.clientService.getService(token_);
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
  }

  public Mono<File> createFolder(String token, String folderName) {
    return Mono.just(token)
      .map(token_ -> {
        try {
          File file;
          File fileMetadata = new File();
          Drive service =this.clientService.getService(token_);

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
  }

  public File setFileParents(String token, String parentFileId, String fileName) {
    File file = new File();
    List<String> parentsIds = new ArrayList<>();

    if(fileName != null) {
      file.setName(fileName);
      parentsIds.add(parentFileId);
      file.setParents(parentsIds);
    }

    return file;
  }

  public Mono<File> appendFile(String token, String parentFileId, String fileName, String mimeType, JsonNode fileContent) {
    return Mono.just(token)
      .map(token_ -> {
        try {
          File result = null;
          Drive service = this.clientService.getService(token_);
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
  }

  public Mono<List<String>> getFileParents(String token, String fileId) {
    return Mono.just(token)
      .map(token_ -> {
        try {
          File file;
          Drive service = this.clientService.getService(token_);      

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
  }
  
  public ResponseEntity<InputStreamResource> downFile(String token, String fileId,  String mimeType) {
    Drive service;
    java.io.File file;
    MediaType contentType;
    FileInputStream input;
    OutputStream outputStream;
    InputStreamResource resource;
    String filePath = "/tmp/" + fileId;

    try {
      service = this.clientService.getService(token);
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
  }

  public Mono<ByteArrayOutputStream> exporFile(String token, String fileId, String mimeType) {
    return Mono.just(token)
      .map(token_ -> {
        try {
          OutputStream outputStream = new ByteArrayOutputStream();
          Drive service = this.clientService.getService(token_);
            
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
  }

  public Mono<File> updateFile(String token, String fileId, String fileName, String mimeType, JsonNode fileContent) {
    return Mono.just(token)
      .map(token_ -> {
        try {
          File result = null;
          Drive service = this.clientService.getService(token_);
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
  }
  
  public Mono<String> deleFile(String token, String fileId) {
    return Mono.just(token)
      .map(token_ -> {
        try {
          Drive service = this.clientService.getService(token_);
          if(service != null) {
            service.files().delete(fileId).execute();
            return fileId;
          }
        } catch (IOException | GeneralSecurityException e) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, token_);
    });
  }
}
