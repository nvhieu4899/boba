package com.example.boba.model.drinkPrice;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface DrinkPriceRepository extends MongoRepository<DrinkPrice, String> {
}
