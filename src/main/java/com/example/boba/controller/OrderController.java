package com.example.boba.controller;


import com.example.boba.ConfigUtils;
import com.example.boba.dto.OrderDetailDTO;
import com.example.boba.model.drink.Drink;
import com.example.boba.model.drink.DrinkRepository;
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
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;

import static com.example.boba.ConfigUtils.AUTHORIZATION_HEADER;

@RestController
@RequestMapping("order")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepos;

    @Autowired
    private DrinkRepository drinkRepository;

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


        String jwt = request.getHeader(AUTHORIZATION_HEADER);

        ApplicationUser user = userInfoFromJwt.getUserFromJwt(jwt);


        List<OrderDetail> orderDetails = new ArrayList<>();
        for (OrderDetailDTO detail : orderDetailList) {
            Optional<DrinkPrice> drinkPriceOps = drinkPriceRepository.findById(detail.getDrinkPriceId());
            Topping topping = null;
            if (detail.getToppingId() != null) {
                Optional<Topping> toppingOps = toppingRepository.findById(detail.getToppingId());
                if (toppingOps.isPresent())
                    topping = toppingOps.get();
            }
            if (drinkPriceOps.isPresent()) {
                Drink drink = drinkRepository.findByPriceListContains(drinkPriceOps.get());
                String name = drink != null ? drink.getName() : "Unknown";
                OrderDetail orderDetail = new OrderDetail(drinkPriceOps.get(), name, detail.getQuantity(), detail.getNote(), topping);
                orderDetails.add(orderDetail);
            }
        }
        List<OrderDetail> savedDetails = orderDetailRepository.saveAll(orderDetails);
        Order order = new Order(user, savedDetails, new Date());
        return orderRepository.save(order);
    }

    @GetMapping("history")
    public List<Order> history(HttpServletRequest request) {
        String jwt = request.getHeader(AUTHORIZATION_HEADER);
        ApplicationUser user = userInfoFromJwt.getUserFromJwt(jwt);

        if (user != null) {
            return orderRepository.findByCustomerId(user.getId());
        } else
            return Collections.emptyList();
    }

    @GetMapping("{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable("orderId") String orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        return optionalOrder.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).build());
    }

}
