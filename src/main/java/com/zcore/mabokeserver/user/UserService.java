package com.zcore.mabokeserver.user;

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
public class UserService {
    private UserRepository driveRepository;
    private Logger logger = LoggerFactory.getLogger(UserService.class);
    
    public UserService(UserRepository driveRepository) {
        this.driveRepository = driveRepository;
    }

    public ResponseEntity<User> add(User drive) {
        /*URI location;
        User saveDrive;
        
        if(drive.getName() != null) {
            saveDrive = driveRepository.save(drive);
            location = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(saveDrive.getId())
                        .toUri();

            return ResponseEntity.created(location).build();
        } else {
            return  ResponseEntity.badRequest().body(drive);
        }*/
        return null;
    }

    public ResponseEntity<List<User>> getDrives(/*Pageable pageable*/) {
        /*Page<Drive> page = driveRepository.findAll(
           PageRequest.of(
                   pageable.getPageNumber(),
                   pageable.getPageSize(),
                   pageable.getSortOr(Sort.by(Sort.Direction.DESC, "amount"))));*/
       //ResponseEntity.ok(page.toList());
        List<User> list = (List<User>) driveRepository.findAll(); 
        return ResponseEntity.ok(list);
    }

    public ResponseEntity<User> findById(Long id) {
        Optional<User> dOptional = driveRepository.findById(id);

        if(dOptional.isPresent()) {
            return ResponseEntity.ok(dOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<User> updateDrive(User drive) {
        /*User dbDrive;
        Optional<User> dOptional = driveRepository.findById(drive.getId());

        if(dOptional.isPresent()) {
            dbDrive = dOptional.get();
            dbDrive.setName(drive.getName());
            driveRepository.save(dbDrive);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }*/
        return null;
    }

    public ResponseEntity<User> deleteDrive(Long id) {
        Optional<User> dOptional = driveRepository.findById(id);

        if(dOptional.isPresent()) {
            driveRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
