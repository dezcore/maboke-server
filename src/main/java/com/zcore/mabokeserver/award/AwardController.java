package com.zcore.mabokeserver.award;

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
@RequestMapping("/award")
public class AwardController {
    private final AwardService driveService;
    //@Autowired
    //private DriveService service;

    public AwardController(AwardService service){
        driveService = service;
    }

    @PostMapping
    public ResponseEntity<Award> add(@RequestBody Award drive) throws Exception {
        return  driveService.add(drive);
    }

    @GetMapping
    public ResponseEntity<List<Award>> getDrives() {
        return driveService.getDrives();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Award> getById(@PathVariable String id) {
        return driveService.findById(id);
    }

    @PutMapping
    public ResponseEntity<Award> updateDrive(@RequestBody Award drive) {
        return driveService.updateDrive(drive);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Award> deleteDrive(@PathVariable("id") String id) {
        return driveService.deleteDrive(id);
    }
}
