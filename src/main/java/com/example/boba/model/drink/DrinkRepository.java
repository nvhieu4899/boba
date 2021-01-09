package com.example.boba.model.drink;

import com.example.boba.model.drinkPrice.DrinkPrice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrinkRepository extends MongoRepository<Drink, String> {

    Drink findByPriceListContains(DrinkPrice drinkPrice);
}
