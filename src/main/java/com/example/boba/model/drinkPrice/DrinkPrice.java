package com.example.boba.model.drinkPrice;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
public class DrinkPrice {
    @Id
    private String id;

    private String size;

    private int price;


    public DrinkPrice(String size, int price) {
        this.size = size;
        this.price = price;
    }
}
