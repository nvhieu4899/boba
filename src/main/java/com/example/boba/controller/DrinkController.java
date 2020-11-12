package com.example.boba.controller;

import com.example.boba.model.category.Category;
import com.example.boba.model.category.CategoryRepository;
import com.example.boba.model.drink.Drink;
import com.example.boba.model.drink.DrinkRepository;
import com.example.boba.model.user.ApplicationUser;
import com.example.boba.request.NewDrinkRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("drink")
public class DrinkController {

    @Autowired
    private DrinkRepository drinkRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("")
    public Page<Drink> getAllDrink(@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                   @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex) {
        return drinkRepository.findAll(PageRequest.of(pageIndex - 1, pageSize));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("")
    public Drink createNewDrink(@RequestBody NewDrinkRequest drinkRequest, Authentication authentication) {


        Optional<Category> category = categoryRepository.findById(drinkRequest.getCategoryId());

        if (!category.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No category found");

        Drink drink = new Drink(category.get(), drinkRequest.getName(), drinkRequest.getImage(), drinkRequest.getDescription(), drinkRequest.getCost());
        return drinkRepository.save(drink);
    }

}
