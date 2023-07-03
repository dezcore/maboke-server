package com.zcore.mabokeserver.category;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    private Logger logger = LoggerFactory.getLogger(CategoryService.class);

    public Mono<ResponseEntity<Category>> add(Category category) {
        return this.categoryRepository.existsByCategory(category.getCategory())
            .flatMap(exist -> {
                if(exist) {
                    return this.categoryRepository.findByCategory(category.getCategory()).flatMap(category1 -> {
                        category.setId(category1.getId());
                        return categoryRepository.save(category).map(category2 -> new ResponseEntity<>(category2, HttpStatus.ACCEPTED));
                    }).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
                } else {
                    return this.categoryRepository.save(category).map(conflict2 -> new ResponseEntity<>(conflict2, HttpStatus.ACCEPTED));
                }
        }).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public Flux<Category> getCategories() {
        return this.categoryRepository.findAll();
        /*return this.categoryRepository.findAll()
        .map(categories -> new ResponseEntity<>(categories, HttpStatus.OK))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));*/
    }
    
    public Flux<Category> getPages() {
        return this.categoryRepository.findAll();
    }

    public Mono<ResponseEntity<Category>> updateCategory(Category category) {
        String id = category.getId();

        return this.categoryRepository.findById(category.getId())
            .flatMap(category1 -> {
                category.setId(id);
                return categoryRepository.save(category)
            .map(category2 -> new ResponseEntity<>(category2, HttpStatus.ACCEPTED));
        }).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public Mono<Void> deleteCategory(String id) {
        return this.categoryRepository.deleteById(id);
    }
}
