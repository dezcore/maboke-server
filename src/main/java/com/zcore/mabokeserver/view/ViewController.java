package com.zcore.mabokeserver.view;

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
@RequestMapping("/view")
public class ViewController {
    private final ViewService driveService;
    //@Autowired
    //private DriveService service;
    
    public ViewController(ViewService service){
        driveService = service;
    }

    @PostMapping
    public ResponseEntity<View> add(@RequestBody View drive) throws Exception {
        return  driveService.add(drive);
    }

    @GetMapping
    public ResponseEntity<List<View>> getDrives() {
        return driveService.getDrives();
    }

    @GetMapping("/{id}")
    public ResponseEntity<View> getById(@PathVariable Long id) {
        return driveService.findById(id);
    }

    @PutMapping
    public ResponseEntity<View> updateDrive(@RequestBody View drive) {
        return driveService.updateDrive(drive);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<View> deleteDrive(@PathVariable("id") Long id) {
        return driveService.deleteDrive(id);
    }
}
