package com.zcore.mabokeserver.view;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class ViewService {
    @Autowired
    private ViewRepository viewRepository;
    private Logger logger = LoggerFactory.getLogger(ViewService.class);

    public ResponseEntity<View> add(View view) {
        URI location;
        View saveView;

        if(view.getUser_id() != null) {
            saveView = viewRepository.save(view);
            location = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(saveView.getId())
                        .toUri();

            return ResponseEntity.created(location).build();
        } else {
            return  ResponseEntity.badRequest().body(view);
        }
    }

    public ResponseEntity<List<View>> getView(/*Pageable pageable*/) {
        /*Page<Drive> page = driveRepository.findAll(
           PageRequest.of(
                   pageable.getPageNumber(),
                   pageable.getPageSize(),
                   pageable.getSortOr(Sort.by(Sort.Direction.DESC, "amount"))));*/
       //ResponseEntity.ok(page.toList());
        List<View> list = (List<View>) viewRepository.findAll(); 
        return ResponseEntity.ok(list);
    }

    public ResponseEntity<View> findById(String id) {
        Optional<View> dOptional = viewRepository.findById(id);

        if(dOptional.isPresent()) {
            return ResponseEntity.ok(dOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<View> updateView(View view) {
        View dbView;
        Optional<View> dOptional = viewRepository.findById(view.getId());

        if(dOptional.isPresent()) {
            dbView = dOptional.get();
            //dbView.setName(drive.getName());
            viewRepository.save(dbView);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    public ResponseEntity<View> deleteView(String id) {
        Optional<View> dOptional = viewRepository.findById(id);

        if(dOptional.isPresent()) {
            viewRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
