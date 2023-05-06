package com.zcore.mabokeserver.drive;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/drive")
public class DriveController {
    @Autowired
    private DriveService service;
    
    @PostMapping
    public ResponseEntity<Drive> add(@RequestBody Drive drive) throws Exception {
        return null;//driveService.add(drive);
    }

    @GetMapping
    public ResponseEntity<List<Drive>> getDrives() {
        return null;//driveService.getDrives();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Drive> getById(@PathVariable Long id) {
        return null;//driveService.findById(id);
    }

    @PutMapping
    public ResponseEntity<Drive> updateDrive(@RequestBody Drive drive) {
        return null;//driveService.updateDrive(drive);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Drive> deleteDrive(@PathVariable("id") Long id) {
        return null;//driveService.deleteDrive(id);
    }
}
