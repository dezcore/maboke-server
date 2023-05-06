package com.zcore.mabokeserver.movie;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class MovieService {
    private MovieRepository driveRepository;
    private Logger logger = LoggerFactory.getLogger(MovieService.class);
    
    public MovieService(MovieRepository driveRepository) {
        this.driveRepository = driveRepository;
    }

    public ResponseEntity<Movie> add(Movie movie) {
        URI location;
        Movie saveDrive;

        if(movie.getVideo().getTitle() != null) {
            saveDrive = driveRepository.save(movie);
            location = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(saveDrive.getId())
                        .toUri();

            return ResponseEntity.created(location).build();
        } else {
            return  ResponseEntity.badRequest().body(movie);
        }
    }

    public ResponseEntity<List<Movie>> getDrives(/*Pageable pageable*/) {
        /*Page<Drive> page = driveRepository.findAll(
           PageRequest.of(
                   pageable.getPageNumber(),
                   pageable.getPageSize(),
                   pageable.getSortOr(Sort.by(Sort.Direction.DESC, "amount"))));*/
       //ResponseEntity.ok(page.toList());
        List<Movie> list = (List<Movie>) driveRepository.findAll(); 
        return ResponseEntity.ok(list);
    }

    public ResponseEntity<Movie> findById(String id) {
        Optional<Movie> dOptional = driveRepository.findById(id);

        if(dOptional.isPresent()) {
            return ResponseEntity.ok(dOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<Movie> updateDrive(Movie movie) {
        Movie dbMovie;
        Optional<Movie> dOptional = driveRepository.findById(movie.getId());

        if(dOptional.isPresent()) {
            dbMovie = dOptional.get();
            //dbMovie.setName(movie.getName());
            driveRepository.save(dbMovie);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Movie> deleteDrive(String id) {
        Optional<Movie> dOptional = driveRepository.findById(id);

        if(dOptional.isPresent()) {
            driveRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
