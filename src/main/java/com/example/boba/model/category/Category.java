package com.example.boba.model.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("category")
public class Category {
    @Id
    private String id;

    private String categoryName;

    private String description;

    public Category(String categoryName, String description) {
        this.categoryName = categoryName;
        this.description = description;
    }
}
