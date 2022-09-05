package com.github.ronnyaraujo.saleapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.ronnyaraujo.saleapi.model.OrderModel;
import com.github.ronnyaraujo.saleapi.service.OrderService;

@RestController
@RequestMapping("sale-api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
	public ResponseEntity<List<OrderModel>> findAll() {
		List<OrderModel> ordersList = orderService.findAll();
		return ResponseEntity.ok().body(ordersList);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<OrderModel> findById(@PathVariable Long id) throws Exception{
		OrderModel order = orderService.findById(id);
		return ResponseEntity.ok().body(order);
	}   
	
	@PostMapping
    public ResponseEntity<OrderModel> createOrder(@RequestBody OrderModel order) {
        OrderModel newOrder = orderService.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
    }
}
