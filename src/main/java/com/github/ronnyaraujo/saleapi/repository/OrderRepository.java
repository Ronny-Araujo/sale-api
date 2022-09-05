package com.github.ronnyaraujo.saleapi.repository;



import com.github.ronnyaraujo.saleapi.model.OrderModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Long> {

}
