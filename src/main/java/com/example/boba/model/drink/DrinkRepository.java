package com.example.boba.model.drink;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrinkRepository extends MongoRepository<Drink, String> {
}
