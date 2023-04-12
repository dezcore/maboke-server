package com.zcore.mabokeserver.drive;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

@Service
public class DriveService {
    private DriveRepository driveRepository;
    private Logger logger = LoggerFactory.getLogger(DriveService.class);

    public DriveService(DriveRepository driveRepository){
        this.driveRepository = driveRepository;
    }

    public ResponseEntity<Drive> add(Drive drive) {
        return  null;//ResponseEntity.created("").build();
    }

    public ResponseEntity<List<Drive>> getDrives(/*Pageable pageable*/) {
        /*Page<Drive> page = driveRepository.findAll(
           PageRequest.of(
                   pageable.getPageNumber(),
                   pageable.getPageSize(),
                   pageable.getSortOr(Sort.by(Sort.Direction.DESC, "amount"))));*/
       //ResponseEntity.ok(page.toList());

        return null;
    }

    public ResponseEntity<Drive> findById(Long id) {
        Optional<Drive> dOptional = driveRepository.findById(id);

        if(dOptional.isPresent()) {
            return ResponseEntity.ok(dOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity updateDrive(Drive drive) {
        Drive dbDrive;
        Optional<Drive> dOptional = driveRepository.findById(drive.getId());

        if(dOptional.isPresent()) {
            dbDrive = dOptional.get();
            //set properties
            //update db
        }

        return null;
    }
    
    public ResponseEntity deleteDrive(Long id) {
        Drive dbDrive;
        Optional<Drive> dOptional = driveRepository.findById(id);

        if(dOptional.isPresent()) {
            //set properties
            //update db
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
