package com.example.boba.model.user;


import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<ApplicationUser, String> {
    ApplicationUser findOneByEmail(String email);

}
