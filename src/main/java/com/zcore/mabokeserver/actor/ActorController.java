package com.zcore.mabokeserver.actor;

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
@RequestMapping("/actor")
public class ActorController {
    private final ActorService driveService;
    //@Autowired
    //private DriveService service;

    public ActorController(ActorService service){
        driveService = service;
    }

    @PostMapping
    public ResponseEntity<Actor> add(@RequestBody Actor drive) throws Exception {
        return  driveService.add(drive);
    }

    @GetMapping
    public ResponseEntity<List<Actor>> getDrives() {
        return driveService.getDrives();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Actor> getById(@PathVariable Long id) {
        return driveService.findById(id);
    }

    @PutMapping
    public ResponseEntity<Actor> updateDrive(@RequestBody Actor drive) {
        return driveService.updateDrive(drive);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Actor> deleteDrive(@PathVariable("id") Long id) {
        return driveService.deleteDrive(id);
    }
}
