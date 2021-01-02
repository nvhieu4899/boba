package com.example.boba.model.topping;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Topping {
    @Id
    private String id;

    private String name;
    private String description;
    private int price;
}
