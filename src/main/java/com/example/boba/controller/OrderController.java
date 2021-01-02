package com.example.boba.controller;


import com.example.boba.ConfigUtils;
import com.example.boba.dto.OrderDetailDTO;
import com.example.boba.model.drinkPrice.DrinkPrice;
import com.example.boba.model.drinkPrice.DrinkPriceRepository;
import com.example.boba.model.order.Order;
import com.example.boba.model.order.OrderRepository;
import com.example.boba.model.orderDetail.OrderDetail;
import com.example.boba.model.orderDetail.OrderDetailRepository;
import com.example.boba.model.topping.Topping;
import com.example.boba.model.topping.ToppingRepository;
import com.example.boba.model.user.ApplicationUser;
import com.example.boba.model.user.UserRepository;
import com.example.boba.service.GetUserInfoFromJwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("order")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepos;

    @Autowired
    private DrinkPriceRepository drinkPriceRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ToppingRepository toppingRepository;

    @Autowired
    private GetUserInfoFromJwt userInfoFromJwt;

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping("")
    public Order createOrder(HttpServletRequest request, @RequestBody List<OrderDetailDTO> orderDetailList) {


        String jwt = request.getHeader(ConfigUtils.AUTHORIZATION_HEADER);

        ApplicationUser user = userInfoFromJwt.getUserFromJwt(jwt);


        List<OrderDetail> orderDetails = new ArrayList<>();
        for (OrderDetailDTO detail : orderDetailList) {
            Optional<DrinkPrice> drinkPriceOps = drinkPriceRepository.findById(detail.getDrinkPriceId());
            Topping topping = null;
            if (detail.getToppingId() != null) {

                Optional<Topping> toppingOps = toppingRepository.findById(detail.getToppingId());
                topping = toppingOps.get();
            }
            if (drinkPriceOps.isPresent()) {
                OrderDetail orderDetail = new OrderDetail(drinkPriceOps.get(), detail.getQuantity(), detail.getNote(), topping);
                orderDetails.add(orderDetail);
            }
        }
        List<OrderDetail> savedDetails = orderDetailRepository.saveAll(orderDetails);
        Order order = new Order(user, savedDetails, new Date());
        return orderRepository.save(order);
    }

}