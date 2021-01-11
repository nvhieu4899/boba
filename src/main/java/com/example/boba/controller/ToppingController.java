package com.example.boba.controller;

import com.example.boba.model.topping.Topping;
import com.example.boba.model.topping.ToppingRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("topping")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class ToppingController {

    @Autowired
    private ToppingRepository toppingRepository;

    @GetMapping("")
    public Page<Topping> getToppings(@RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex,
                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return toppingRepository.findAll(PageRequest.of(pageIndex - 1, pageSize));
    }


    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Topping> createTopping(@RequestBody List<Topping> toppings) {
        return toppingRepository.saveAll(toppings);
    }

}
