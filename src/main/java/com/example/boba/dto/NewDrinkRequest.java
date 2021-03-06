package com.example.boba.dto;

import com.example.boba.model.category.Category;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewDrinkRequest {

    @JsonSerialize(using = ToStringSerializer.class)
    private String categoryId;

    private String name;

    private String image;

    private String description;

    private HashMap<String, Integer> cost;

}
