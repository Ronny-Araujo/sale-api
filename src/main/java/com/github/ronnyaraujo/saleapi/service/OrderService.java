package com.github.ronnyaraujo.saleapi.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ronnyaraujo.saleapi.enumeration.OrderStatus;
import com.github.ronnyaraujo.saleapi.exceptions.ObjectNotFoundException;
import com.github.ronnyaraujo.saleapi.model.OrderItemModel;
import com.github.ronnyaraujo.saleapi.model.OrderModel;
import com.github.ronnyaraujo.saleapi.model.PaymentModel;
import com.github.ronnyaraujo.saleapi.repository.OrderItemRepository;
import com.github.ronnyaraujo.saleapi.repository.OrderRepository;
import com.github.ronnyaraujo.saleapi.repository.PaymentRepository;

@Service
public class OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    public List<OrderModel> findAll() {
        LOGGER.info("Getting all orders");
        return orderRepository.findAll();
    }

    public OrderModel findById(Long id) throws ObjectNotFoundException{
        LOGGER.info("Getting order by ID: {}", id);
        
        Optional<OrderModel> order = orderRepository.findById(id);
        return order.orElseThrow(() -> new ObjectNotFoundException("Order not found Id: " + id));
    }

    public OrderModel createOrder(OrderModel order) {
        LOGGER.info("Creating order");

        order.setId(null);
        order.setInstant(Instant.now());
        order.setUserModel(userService.findById(order.getUserModel().getId()));
        order.setOrderStatus(OrderStatus.WAITING_PAYMENT);
        
        PaymentModel paymentModel = new PaymentModel(null, Instant.now(), order);
        order.setPayment(paymentModel);
        
        order = orderRepository.save(order);
        paymentRepository.save(paymentModel);        
        
        for (OrderItemModel orderItem : order.getItems()) {
            orderItem.setProductModel(productService.findById(orderItem.getProductModel().getId()));
            orderItem.setPrice(orderItem.getProductModel().getPrice());
            orderItem.setQuantity(orderItem.getQuantity());
            orderItem.setOrderModel(order);
        }

        orderItemRepository.saveAll(order.getItems());        

        return order;
    }
}
