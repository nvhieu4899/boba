package com.example.boba.controller;

import com.example.boba.model.category.Category;
import com.example.boba.model.category.CategoryRepository;
import com.example.boba.model.drink.Drink;
import com.example.boba.model.drink.DrinkRepository;
import com.example.boba.model.drinkPrice.DrinkPrice;
import com.example.boba.model.drinkPrice.DrinkPriceRepository;
import com.example.boba.dto.NewDrinkRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.security.RolesAllowed;
import java.util.*;

@RestController
@RequestMapping("drink")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DrinkController {

    @Autowired
    private DrinkRepository drinkRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DrinkPriceRepository drinkPriceRepository;

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

        Drink drink = new Drink(category.get(), drinkRequest.getName(), drinkRequest.getImage(), drinkRequest.getDescription());

        Drink savedDrink = drinkRepository.save(drink);

        List<DrinkPrice> drinkPriceSet = new ArrayList<>();
        for (Map.Entry<String, Integer> priceBySize : drinkRequest.getCost().entrySet()) {
            DrinkPrice drinkPrice = new DrinkPrice(priceBySize.getKey(), priceBySize.getValue());
            drinkPriceSet.add(drinkPrice);
        }
        savedDrink.getPriceList().addAll(drinkPriceSet);
        drinkPriceRepository.saveAll(drinkPriceSet);
        return drinkRepository.save(savedDrink);
    }

    @GetMapping("{drinkId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Drink> getDrinkDetails(@PathVariable("drinkId") String drinkId) {
        Optional<Drink> drink = drinkRepository.findById(drinkId);

        return drink.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}
