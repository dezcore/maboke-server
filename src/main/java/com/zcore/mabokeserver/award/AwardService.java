package com.zcore.mabokeserver.award;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

@Service
public class AwardService {
    private AwardRepository driveRepository;
    private Logger logger = LoggerFactory.getLogger(AwardService.class);
    
    public AwardService(AwardRepository driveRepository) {
        this.driveRepository = driveRepository;
    }

    public ResponseEntity<Award> add(Award drive) {
        /*URI location;
        Award saveDrive;

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

    public ResponseEntity<List<Award>> getDrives(/*Pageable pageable*/) {
        /*Page<Drive> page = driveRepository.findAll(
           PageRequest.of(
                   pageable.getPageNumber(),
                   pageable.getPageSize(),
                   pageable.getSortOr(Sort.by(Sort.Direction.DESC, "amount"))));*/
       //ResponseEntity.ok(page.toList());
        List<Award> list = (List<Award>) driveRepository.findAll(); 
        return ResponseEntity.ok(list);
    }

    public ResponseEntity<Award> findById(String id) {
        Optional<Award> dOptional = driveRepository.findById(id);

        if(dOptional.isPresent()) {
            return ResponseEntity.ok(dOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<Award> updateDrive(Award drive) {
        /*Award dbDrive;
        Optional<Award> dOptional = driveRepository.findById(drive.getId());

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

    public ResponseEntity<Award> deleteDrive(String id) {
        Optional<Award> dOptional = driveRepository.findById(id);

        if(dOptional.isPresent()) {
            driveRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
