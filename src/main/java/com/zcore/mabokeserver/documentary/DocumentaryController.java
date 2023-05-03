package com.zcore.mabokeserver.documentary;

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
@RequestMapping("/documentary")
public class DocumentaryController {
    private final DocumentaryService driveService;
    //@Autowired
    //private DriveService service;
    
    public DocumentaryController(DocumentaryService service){
        driveService = service;
    }

    @PostMapping
    public ResponseEntity<Documentary> add(@RequestBody Documentary drive) throws Exception {
        return  driveService.add(drive);
    }

    @GetMapping
    public ResponseEntity<List<Documentary>> getDrives() {
        return driveService.getDrives();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Documentary> getById(@PathVariable Long id) {
        return driveService.findById(id);
    }

    @PutMapping
    public ResponseEntity<Documentary> updateDrive(@RequestBody Documentary drive) {
        return driveService.updateDrive(drive);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Documentary> deleteDrive(@PathVariable("id") Long id) {
        return driveService.deleteDrive(id);
    }
}
