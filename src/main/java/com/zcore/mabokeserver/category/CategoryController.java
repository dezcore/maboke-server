package com.zcore.mabokeserver.category;

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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Mono<ResponseEntity<Category>> add(@RequestBody Category category) throws Exception {
        return  this.categoryService.add(category);
    }

    @GetMapping
    public Flux<Category> getCategories() {
        return this.categoryService.getCategories();
    }

    @PutMapping
    public Mono<ResponseEntity<Category>> updateView(@RequestBody Category category) {
        return this.categoryService.updateCategory(category);
    }

    @DeleteMapping("/{id}")
    public  Mono<Void> deleteCategory(@PathVariable("id") String id) {
        return this.categoryService.deleteCategory(id);
    }
}
