package com.zcore.mabokeserver.serie;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class SerieService {
    private SerieRepository driveRepository;
    private Logger logger = LoggerFactory.getLogger(SerieService.class);
    
    public SerieService(SerieRepository driveRepository) {
        this.driveRepository = driveRepository;
    }

    public ResponseEntity<Serie> add(Serie serie) {
        URI location;
        Serie saveDrive;
        
        if(serie.getImg() != null) {
            saveDrive = driveRepository.save(serie);
            location = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(saveDrive.getId())
                        .toUri();

            return ResponseEntity.created(location).build();
        } else {
            return  ResponseEntity.badRequest().body(serie);
        }
    }

    public ResponseEntity<List<Serie>> getDrives(/*Pageable pageable*/) {
        /*Page<Drive> page = driveRepository.findAll(
           PageRequest.of(
                   pageable.getPageNumber(),
                   pageable.getPageSize(),
                   pageable.getSortOr(Sort.by(Sort.Direction.DESC, "amount"))));*/
       //ResponseEntity.ok(page.toList());
        List<Serie> list = (List<Serie>) driveRepository.findAll(); 
        return ResponseEntity.ok(list);
    }

    public ResponseEntity<Serie> findById(String id) {
        Optional<Serie> dOptional = driveRepository.findById(id);

        if(dOptional.isPresent()) {
            return ResponseEntity.ok(dOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<Serie> updateDrive(Serie serie) {
        Serie dbDrive;
        Optional<Serie> dOptional = driveRepository.findById(serie.getId());

        if(dOptional.isPresent()) {
            dbDrive = dOptional.get();
            //dbDrive.setName(serie.getName());
            driveRepository.save(dbDrive);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Serie> deleteDrive(String id) {
        Optional<Serie> dOptional = driveRepository.findById(id);

        if(dOptional.isPresent()) {
            driveRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
