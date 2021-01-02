package com.example.boba.model.topping;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToppingRepository extends MongoRepository<Topping, String> {
}
