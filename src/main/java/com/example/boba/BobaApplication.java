package com.example.boba;

import com.example.boba.model.category.Category;
import com.example.boba.model.drink.Drink;
import com.example.boba.model.order.Order;
import com.example.boba.model.user.ApplicationUser;
import com.example.boba.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class BobaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BobaApplication.class, args);
    }

}
