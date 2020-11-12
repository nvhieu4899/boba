package com.example.boba.model.order;

import com.example.boba.model.drink.Drink;
import com.example.boba.model.user.ApplicationUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;

@Data
@NoArgsConstructor
@Document("order")
@AllArgsConstructor
public class Order {
    @Id
    private String id;

    private ApplicationUser customer;

    private ArrayList<Drink> drinks;

    private Integer cost;

    private Date creationDate;

}
