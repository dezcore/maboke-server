package com.zcore.mabokeserver.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private ViewService viewService;

    @PostMapping
    public ResponseEntity<View> add(@RequestBody View drive) throws Exception {
        return  viewService.add(drive);
    }

    @GetMapping
    public ResponseEntity<List<View>> getDrives() {
        return viewService.getView();
    }

    @GetMapping("/{id}")
    public ResponseEntity<View> getById(@PathVariable String id) {
        return viewService.findById(id);
    }
    
    @PutMapping
    public ResponseEntity<View> updateView(@RequestBody View drive) {
        return viewService.updateView(drive);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<View> deleteView(@PathVariable("id") String id) {
        return viewService.deleteView(id);
    }
}
