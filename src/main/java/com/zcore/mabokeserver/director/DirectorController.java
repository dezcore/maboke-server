package com.zcore.mabokeserver.director;

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
@RequestMapping("/director")
public class DirectorController {
    private final DirectorService driveService;
    //@Autowired
    //private DriveService service;

    public DirectorController(DirectorService service){
        driveService = service;
    }

    @PostMapping
    public ResponseEntity<Director> add(@RequestBody Director drive) throws Exception {
        return  driveService.add(drive);
    }

    @GetMapping
    public ResponseEntity<List<Director>> getDrives() {
        return driveService.getDrives();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Director> getById(@PathVariable String id) {
        return driveService.findById(id);
    }

    @PutMapping
    public ResponseEntity<Director> updateDrive(@RequestBody Director drive) {
        return driveService.updateDrive(drive);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Director> deleteDrive(@PathVariable("id") String id) {
        return driveService.deleteDrive(id);
    }
}
