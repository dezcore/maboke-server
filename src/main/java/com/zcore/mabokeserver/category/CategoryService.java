package com.zcore.mabokeserver.category;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    private Logger logger = LoggerFactory.getLogger(CategoryService.class);

    public Mono<ResponseEntity<Category>> add(Category category) {
        return this.categoryRepository.existsByPageAndCategory(category.getPage(), category.getCategory())
            .flatMap(exist -> {
                Mono<Category> moConflict = (!exist ? 
                    this.categoryRepository.save(category) : 
                    this.categoryRepository.findByPageAndCategory(category.getPage(), category.getCategory())
                );
                return moConflict.map(conflict2 -> new ResponseEntity<>(conflict2, HttpStatus.ACCEPTED));
        }).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    public Mono<Page<Category>> getCategories(Pageable paging) {
        return this.categoryRepository.count()
            .flatMap(categoryCount -> {
                return this.categoryRepository.findAll()
                    .skip((paging.getPageNumber()-1) * paging.getPageSize())
                    .take(paging.getPageSize())
                    .collectList().map(series -> new PageImpl<Category>(series, paging, categoryCount));
        });
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
