package com.zcore.mabokeserver.season;

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
@RequestMapping("/season")
public class SeasonController {
    private final SeasonService driveService;
    //@Autowired
    //private DriveService service;
    
    public SeasonController(SeasonService service){
        driveService = service;
    }

    @PostMapping
    public ResponseEntity<Season> add(@RequestBody Season drive) throws Exception {
        return  driveService.add(drive);
    }

    @GetMapping
    public ResponseEntity<List<Season>> getDrives() {
        return driveService.getDrives();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Season> getById(@PathVariable String id) {
        return driveService.findById(id);
    }

    @PutMapping
    public ResponseEntity<Season> updateDrive(@RequestBody Season drive) {
        return driveService.updateDrive(drive);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Season> deleteDrive(@PathVariable("id") String id) {
        return driveService.deleteDrive(id);
    }
}
