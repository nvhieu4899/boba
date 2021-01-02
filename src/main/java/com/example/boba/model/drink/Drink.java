package com.example.boba.model.drink;

import com.example.boba.model.category.Category;
import com.example.boba.model.drinkPrice.DrinkPrice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("drink")
public class Drink {
    @Id
    private String id;

    @Field("category")
    @DBRef
    private Category category;

    private String name;

    private String image;

    private String description;

    private Integer cost;

    @DBRef(lazy = true)
    private List<DrinkPrice> priceList;

    public Drink(Category category, String name, String image, String description) {
        this.category = category;
        this.name = name;
        this.image = image;
        this.description = description;
        priceList = new ArrayList<>();
    }
}
