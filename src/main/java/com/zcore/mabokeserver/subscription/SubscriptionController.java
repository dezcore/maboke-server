package com.zcore.mabokeserver.subscription;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {
    private final SubscriptionService driveService;
    //@Autowired
    //private DriveService service;
    
    public SubscriptionController(SubscriptionService service){
        driveService = service;
    }

    /*@PostMapping
    public ResponseEntity<Subscription> add(@RequestBody Subscription drive) throws Exception {
        return  driveService.add(drive);
    }

    @GetMapping
    public ResponseEntity<List<Subscription>> getDrives() {
        return driveService.getDrives();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subscription> getById(@PathVariable Long id) {
        return driveService.findById(id);
    }

    @PutMapping
    public ResponseEntity<Subscription> updateDrive(@RequestBody Subscription drive) {
        return driveService.updateDrive(drive);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Subscription> deleteDrive(@PathVariable("id") Long id) {
        return driveService.deleteDrive(id);
    }*/
}
