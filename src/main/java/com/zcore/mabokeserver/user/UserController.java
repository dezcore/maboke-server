package com.zcore.mabokeserver.user;

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
@RequestMapping("/user")
public class UserController {
    private final UserService driveService;
    //@Autowired
    //private DriveService service;
    
    public UserController(UserService service){
        driveService = service;
    }

    @PostMapping
    public ResponseEntity<User> add(@RequestBody User drive) throws Exception {
        return  driveService.add(drive);
    }

    @GetMapping
    public ResponseEntity<List<User>> getDrives() {
        return driveService.getDrives();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return driveService.findById(id);
    }

    @PutMapping
    public ResponseEntity<User> updateDrive(@RequestBody User drive) {
        return driveService.updateDrive(drive);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteDrive(@PathVariable("id") Long id) {
        return driveService.deleteDrive(id);
    }
}
