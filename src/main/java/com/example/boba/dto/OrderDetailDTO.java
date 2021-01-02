package com.example.boba.dto;

import lombok.Data;

import java.util.HashMap;

@Data
public class OrderDetailDTO {
    private String drinkPriceId;
    private int quantity;
    private String note;
    private String toppingId;
}
