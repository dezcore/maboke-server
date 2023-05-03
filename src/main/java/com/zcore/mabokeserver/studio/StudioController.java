package com.zcore.mabokeserver.studio;

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
@RequestMapping("/studio")
public class StudioController {
    private final StudioService driveService;
    //@Autowired
    //private DriveService service;

    public StudioController(StudioService service){
        driveService = service;
    }

    @PostMapping
    public ResponseEntity<Studio> add(@RequestBody Studio drive) throws Exception {
        return  driveService.add(drive);
    }

    @GetMapping
    public ResponseEntity<List<Studio>> getDrives() {
        return driveService.getDrives();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Studio> getById(@PathVariable Long id) {
        return driveService.findById(id);
    }

    @PutMapping
    public ResponseEntity<Studio> updateDrive(@RequestBody Studio drive) {
        return driveService.updateDrive(drive);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Studio> deleteDrive(@PathVariable("id") Long id) {
        return driveService.deleteDrive(id);
    }
}
