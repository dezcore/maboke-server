package com.zcore.mabokeserver.google.gdrive;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class GDriveService {
  @Autowired
  private GDriveRepository gRepository;
  private Logger logger = LoggerFactory.getLogger(GDriveService.class);

  public Mono<ResponseEntity<GDrive>> findByName(String name) {
    return this.gRepository.findByName(name)
      .map(serie -> new ResponseEntity<>(serie, HttpStatus.OK))
      .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
  
  public Mono<ResponseEntity<Boolean>> existByName(String name) {
    return this.gRepository.existsByName(name)
      .map(serie -> new ResponseEntity<>(serie, HttpStatus.OK))
      .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
  
  public Mono<ResponseEntity<List<GDrive>>> findByNames(String[] names) {
    logger.info("Names : " + names);
    return Mono.just(names)
      .map(names_ -> {
        List<GDrive> drives = new ArrayList<>();
        for(String name : names) {
          findByName(name).doOnNext(drive -> {
            drives.add(drive.getBody());
          }).subscribe();
      }
      return new ResponseEntity<>(drives, HttpStatus.ACCEPTED);
    }).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }


  public Mono<ResponseEntity<GDrive>> addGFile(GDrive gFile) {
    return (
      this.gRepository.existsByName(gFile.getName()).flatMap(exist->{
        if(exist) {
          return this.gRepository.findByName(gFile.getName()).flatMap( gFile1->{
            gFile.setId(gFile1.getId());
            return gRepository.save(gFile).map(gFile2 -> new ResponseEntity<>(gFile2, HttpStatus.ACCEPTED));
          }).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } else {
          return this.gRepository.save(gFile).map(gFile2 -> new ResponseEntity<>(gFile2, HttpStatus.ACCEPTED));
        }
      })
    );
  }

  public Mono<List<GDrive>> addGFiles(List<GDrive> gFiles) {
    return Mono.just(gFiles)
      .map(gFiles_ -> {
        List<GDrive> drives = new ArrayList<>();
        for(GDrive gFile : gFiles) {
          addGFile(gFile).doOnNext(drive -> {
            drives.add(drive.getBody());
          }).subscribe();
        }

        return drives;
      });
  }

  public Mono<ResponseEntity<GDrive>> update(GDrive drive) {
        String id = drive.getId();

        return this.gRepository.findById(drive.getId())
            .flatMap(gdrive1 -> {
                drive.setId(id);
                return  gRepository.save(drive)
            .map(drive2 -> new ResponseEntity<>(drive2, HttpStatus.ACCEPTED));
        }).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  public Mono<Void> delete(String id) {
    return this.gRepository.deleteById(id);
  }

  public Mono<Void> deleteAll(List<String> ids) {
    return this.gRepository.deleteAllById(ids);
  }
}
