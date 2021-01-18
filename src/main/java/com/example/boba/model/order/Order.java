package com.example.boba.model.order;

import com.example.boba.model.drink.Drink;
import com.example.boba.model.drinkPrice.DrinkPrice;
import com.example.boba.model.drinkPrice.DrinkPriceRepository;
import com.example.boba.model.orderDetail.OrderDetail;
import com.example.boba.model.user.ApplicationUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
@Document("order")
@AllArgsConstructor
public class Order {
    @Id
    private String id;

    private ApplicationUser customer;

    @DBRef
    private List<OrderDetail> orderDetailList;

    private int cost;

    private Date creationDate;

    private boolean isProcessed;

    @JsonIgnore
    public int calculateTotal() {
        cost = 0;
        for (OrderDetail orderDetail : orderDetailList) {
            cost += orderDetail.calculateSubTotal();
        }
        return cost;
    }

    public Order(ApplicationUser customer, List<OrderDetail> orderDetailList, Date creationDate) {
        this.customer = customer;
        this.orderDetailList = orderDetailList;
        this.creationDate = creationDate;
        isProcessed = false;
        calculateTotal();
    }
}
