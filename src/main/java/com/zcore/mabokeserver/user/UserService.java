package com.zcore.mabokeserver.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class UserService {
    @Autowired 
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    public ResponseEntity<User> add(User user) {
        URI location;
        User saveUser;
        logger.info("test user");
        if(user.getName() != null) {           
           user.setPassword(passwordEncoder.encode(user.getPassword()));

            saveUser = userRepository.save(user);
            location = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(saveUser.getId())
                        .toUri();

            return ResponseEntity.created(location).build();
        } else {
            return  ResponseEntity.badRequest().body(user);
        }
    }

    public ResponseEntity<List<User>> getUsers(/*Pageable pageable*/) {
        /*Page<Drive> page = driveRepository.findAll(
           PageRequest.of(
                   pageable.getPageNumber(),
                   pageable.getPageSize(),
                   pageable.getSortOr(Sort.by(Sort.Direction.DESC, "amount"))));*/
       //ResponseEntity.ok(page.toList());
        List<User> list = (List<User>) userRepository.findAll(); 
        return ResponseEntity.ok(list);
    }

    public ResponseEntity<User> findById(Integer id) {
        Optional<User> dOptional = userRepository.findById(id);

        if(dOptional.isPresent()) {
            return ResponseEntity.ok(dOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<User> updateUser(User user) {
        User dbUser;
        Optional<User> dOptional = userRepository.findById(user.getId());

        if(dOptional.isPresent()) {
            dbUser = dOptional.get();
            dbUser.setName(user.getName());
            userRepository.save(dbUser);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<User> deleteUser(Integer id) {
        Optional<User> dOptional = userRepository.findById(id);

        if(dOptional.isPresent()) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
