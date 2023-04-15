package com.zcore.mabokeserver.drive;

import java.util.List;
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
    private final DriveService driveService;
    //@Autowired
    //private DriveService service;
    
    public DriveController(DriveService service){
        driveService = service;
    }

    @PostMapping
    public ResponseEntity<Drive> add(@RequestBody Drive drive) throws Exception {
        return  driveService.add(drive);
    }

    @GetMapping
    public ResponseEntity<List<Drive>> getDrives() {
        return driveService.getDrives();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Drive> getById(@PathVariable Long id) {
        return driveService.findById(id);
    }

    @PutMapping
    public ResponseEntity<Drive> updateDrive(@RequestBody Drive drive) {
        return driveService.updateDrive(drive);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Drive> deleteDrive(@PathVariable("id") Long id) {
        return driveService.deleteDrive(id);
    }
}
