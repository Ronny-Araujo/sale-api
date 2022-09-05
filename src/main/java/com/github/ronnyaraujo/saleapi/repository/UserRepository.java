package com.github.ronnyaraujo.saleapi.repository;



import com.github.ronnyaraujo.saleapi.model.UserModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

}
