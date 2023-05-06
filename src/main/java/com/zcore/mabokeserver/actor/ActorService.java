package com.zcore.mabokeserver.actor;

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
public class ActorService {
    private ActorRepository driveRepository;
    private Logger logger = LoggerFactory.getLogger(ActorService.class);
    
    public ActorService(ActorRepository driveRepository) {
        this.driveRepository = driveRepository;
    }

    public ResponseEntity<Actor> add(Actor drive) {
        URI location;
        Actor saveDrive;

        if(drive.getName() != null) {
            saveDrive = driveRepository.save(drive);
            location = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(saveDrive.getId())
                        .toUri();

            return ResponseEntity.created(location).build();
        } else {
            return  ResponseEntity.badRequest().body(drive);
        }
    }

    public ResponseEntity<List<Actor>> getDrives(/*Pageable pageable*/) {
        /*Page<Drive> page = driveRepository.findAll(
           PageRequest.of(
                   pageable.getPageNumber(),
                   pageable.getPageSize(),
                   pageable.getSortOr(Sort.by(Sort.Direction.DESC, "amount"))));*/
       //ResponseEntity.ok(page.toList());
        List<Actor> list = (List<Actor>) driveRepository.findAll(); 
        return ResponseEntity.ok(list);
    }

    public ResponseEntity<Actor> findById(String id) {
        Optional<Actor> dOptional = driveRepository.findById(id);

        if(dOptional.isPresent()) {
            return ResponseEntity.ok(dOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<Actor> updateDrive(Actor drive) {
        Actor dbDrive;
        Optional<Actor> dOptional = driveRepository.findById(drive.getId());

        if(dOptional.isPresent()) {
            dbDrive = dOptional.get();
            dbDrive.setName(drive.getName());
            driveRepository.save(dbDrive);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Actor> deleteDrive(String id) {
        Optional<Actor> dOptional = driveRepository.findById(id);

        if(dOptional.isPresent()) {
            driveRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
