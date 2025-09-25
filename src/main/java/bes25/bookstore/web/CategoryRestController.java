package bes25.bookstore.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import bes25.bookstore.domain.Category;
import bes25.bookstore.domain.CategoryRepository;

@RestController
public class CategoryRestController {
    private final CategoryRepository categoryRepository;

    public CategoryRestController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Hae kaikki kategoriat JSONina
    @GetMapping("/categories")
    public Iterable<Category> getCategories() {
        return categoryRepository.findAll();
    }

    // Lisää uusi kategoria JSONilla
    @PostMapping("/categories")
    public Category addCategory(@RequestBody Category newCategory) {
        return categoryRepository.save(newCategory);
    }
}
