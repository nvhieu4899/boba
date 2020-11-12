package com.example.boba.controller;

import com.example.boba.model.category.Category;
import com.example.boba.model.category.CategoryRepository;
import com.example.boba.model.drink.DrinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("category")
public class CategoryController {


    @Autowired
    private DrinkRepository drinkRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @GetMapping("")
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("")
    public Category createNewCategory(@RequestBody Map<String, String> request) {
        String categoryName = request.getOrDefault("name", "new Category");
        String description = request.getOrDefault("description", "description");
        Category newCate = new Category(categoryName, description);
        return categoryRepository.save(newCate);
    }

}
