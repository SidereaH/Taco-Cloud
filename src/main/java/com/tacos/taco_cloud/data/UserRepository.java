package com.tacos.taco_cloud.data;

import org.springframework.data.repository.CrudRepository;

import com.tacos.taco_cloud.models.*;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
