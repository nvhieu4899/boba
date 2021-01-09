package com.example.boba.model.orderDetail;


import com.example.boba.model.drink.Drink;
import com.example.boba.model.drinkPrice.DrinkPrice;
import com.example.boba.model.drinkPrice.DrinkPriceRepository;
import com.example.boba.model.topping.Topping;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class OrderDetail {

    @Id
    private String id;
    private DrinkPrice drinkPrice;
    private String drinkName;
    private int quantity;
    private String note;
    private int subtotal;
    private Topping topping;

    @JsonIgnore
    public int calculateSubTotal() {
        subtotal = drinkPrice.getPrice() * quantity;
        if (topping != null) {
            subtotal += topping.getPrice() * quantity;
        }
        return subtotal;
    }

    public OrderDetail(DrinkPrice drinkPrice, String drinkName, int quantity, String note, Topping topping) {
        this.drinkPrice = drinkPrice;
        this.quantity = quantity;
        this.note = note;
        this.topping = topping;
        this.drinkName = drinkName;
        calculateSubTotal();
    }
}
