package com.zcore.mabokeserver.drive;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/drive")
public class DriveController {
    private DriveRepository driveRepository;

    public DriveController(DriveRepository driveRepository){
        this.driveRepository = driveRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Drive> findById(@PathVariable Long id) {
        Optional<Drive> dOptional = driveRepository.findById(id);
        
        if(dOptional.isPresent()) {
            return ResponseEntity.ok(dOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
