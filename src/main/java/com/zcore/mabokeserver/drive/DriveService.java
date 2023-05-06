package com.zcore.mabokeserver.drive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DriveService {
    private Logger logger = LoggerFactory.getLogger(DriveService.class);

    /*public ResponseEntity<Drive> add(Drive drive) {
        URI location;
        Drive saveDrive;
        
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

    public ResponseEntity<List<Drive>> getDrives(Pageable pageable) {
        Page<Drive> page = driveRepository.findAll(
           PageRequest.of(
                   pageable.getPageNumber(),
                   pageable.getPageSize(),
                   pageable.getSortOr(Sort.by(Sort.Direction.DESC, "amount"))));
       //ResponseEntity.ok(page.toList());
        List<Drive> list = (List<Drive>) driveRepository.findAll(); 
        return ResponseEntity.ok(list);
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
    public ResponseEntity<Drive> updateDrive(Drive drive) {
        Drive dbDrive;
        Optional<Drive> dOptional = driveRepository.findById(drive.getId());

        if(dOptional.isPresent()) {
            dbDrive = dOptional.get();
            dbDrive.setName(drive.getName());
            driveRepository.save(dbDrive);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Drive> deleteDrive(Long id) {
        Optional<Drive> dOptional = driveRepository.findById(id);

        if(dOptional.isPresent()) {
            driveRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/
}
