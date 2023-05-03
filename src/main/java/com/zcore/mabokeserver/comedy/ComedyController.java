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
@RequestMapping("/comedy")
public class ComedyController {
    private final ComedyService driveService;
    //@Autowired
    //private DriveService service;
    
    public ComedyController(ComedyService service){
        driveService = service;
    }

    @PostMapping
    public ResponseEntity<Comedy> add(@RequestBody Comedy drive) throws Exception {
        return  driveService.add(drive);
    }

    @GetMapping
    public ResponseEntity<List<Comedy>> getDrives() {
        return driveService.getDrives();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comedy> getById(@PathVariable Long id) {
        return driveService.findById(id);
    }

    @PutMapping
    public ResponseEntity<Comedy> updateDrive(@RequestBody Comedy drive) {
        return driveService.updateDrive(drive);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Comedy> deleteDrive(@PathVariable("id") Long id) {
        return driveService.deleteDrive(id);
    }
}
