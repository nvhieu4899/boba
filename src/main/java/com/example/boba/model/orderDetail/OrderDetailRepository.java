package com.example.boba.model.orderDetail;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends MongoRepository<OrderDetail, String> {
}
