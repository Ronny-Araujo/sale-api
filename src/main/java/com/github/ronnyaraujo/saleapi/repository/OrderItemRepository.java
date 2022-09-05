package com.github.ronnyaraujo.saleapi.repository;

import com.github.ronnyaraujo.saleapi.model.OrderItemModel;
import com.github.ronnyaraujo.saleapi.model.id.OrderItemId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemModel, OrderItemId> {

}
