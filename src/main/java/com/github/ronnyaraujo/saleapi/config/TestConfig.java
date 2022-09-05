package com.github.ronnyaraujo.saleapi.config;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.github.ronnyaraujo.saleapi.enumeration.OrderStatus;
import com.github.ronnyaraujo.saleapi.model.CategoryModel;
import com.github.ronnyaraujo.saleapi.model.OrderItemModel;
import com.github.ronnyaraujo.saleapi.model.OrderModel;
import com.github.ronnyaraujo.saleapi.model.PaymentModel;
import com.github.ronnyaraujo.saleapi.model.ProductModel;
import com.github.ronnyaraujo.saleapi.model.UserModel;
import com.github.ronnyaraujo.saleapi.repository.CategoryRepository;
import com.github.ronnyaraujo.saleapi.repository.OrderItemRepository;
import com.github.ronnyaraujo.saleapi.repository.OrderRepository;
import com.github.ronnyaraujo.saleapi.repository.ProductRepository;
import com.github.ronnyaraujo.saleapi.repository.UserRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired 
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {

        UserModel u1 = new UserModel(null, "Maria Silva", "maria@gmail.com", "988888888", "123456");
        UserModel u2 = new UserModel(null, "João Silva", "joao@gmail.com", "977777777", "123456");
        userRepository.saveAll(Arrays.asList(u1, u2));

        ProductModel p1 = new ProductModel(null, "Kafka: The Definitive Guide", "Quisque ut justo tempor, finibus sem ", new BigDecimal("90.50"), "");
		ProductModel p2 = new ProductModel(null, "Smart TV", "Aenean semper arcu sit amet lorem luctus, id c tincidunt", new BigDecimal("2190.0"), "");
		ProductModel p3 = new ProductModel(null, "Macbook Pro", "Phasellus fermetum eros vestibulum consectetur ornare", new BigDecimal("1250.0"), "");
		ProductModel p4 = new ProductModel(null, "PC Gamer", "Quisque faucibus lorem tincidunt magna fermentum commodo", new BigDecimal("1200.0"), "");
		ProductModel p5 = new ProductModel(null, "Microservices with Spring Boot", "Donec commodo uam dignissim rutrum", new BigDecimal("100.99") , "");
		ProductModel p6 = new ProductModel(null, "Smartphone", "Vestibulum ante ipsum primis fas posuere cubilia curae", new BigDecimal("50.99") , "");

        productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5,p6));

        CategoryModel cat1 = new CategoryModel(null, "Electronics");
		CategoryModel cat2 = new CategoryModel(null, "Books");
		CategoryModel cat3 = new CategoryModel(null, "Computers");

        cat1.getProducts().addAll(Arrays.asList(p2, p3, p4));
        cat2.getProducts().addAll(Arrays.asList(p1, p5));
        cat3.getProducts().addAll(Arrays.asList(p3, p4));

        categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3));

        p1.getCategories().add(cat2);
		p2.getCategories().add(cat1);
		p3.getCategories().add(cat1);
		p3.getCategories().add(cat3);
		p4.getCategories().add(cat3);
		p5.getCategories().add(cat2);

        productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));

        OrderModel o1 = new OrderModel(null, Instant.parse("2017-06-20T19:53:07Z"), OrderStatus.WAITING_PAYMENT, u1);
        OrderModel o2 = new OrderModel(null, Instant.parse("2018-06-20T19:53:07Z"), OrderStatus.toEnum(2), u2);
        OrderModel o3 = new OrderModel(null, Instant.parse("2019-06-20T19:53:07Z"), OrderStatus.SHIPPED, u1);
        OrderModel o4 = new OrderModel(null, Instant.parse("2020-06-20T19:53:07Z"), OrderStatus.DELIVERED, u2);
        OrderModel o5 = new OrderModel(null, Instant.parse("2021-06-20T19:53:07Z"), OrderStatus.CANCELED, u1);

        orderRepository.saveAll(Arrays.asList(o1, o2, o3, o4, o5));

        PaymentModel paymentModel = new PaymentModel(null, Instant.now(), o1);
        o1.setPayment(paymentModel);
        o1.setOrderStatus(OrderStatus.PAID);
        orderRepository.save(o1);

        OrderItemModel oi1 = new OrderItemModel(o1, p1, 2, p1.getPrice());
		OrderItemModel oi2 = new OrderItemModel(o1, p3, 1, p3.getPrice());
		OrderItemModel oi3 = new OrderItemModel(o2, p3, 2, p3.getPrice());
		OrderItemModel oi4 = new OrderItemModel(o3, p4, 2, p4.getPrice());
		OrderItemModel oi5 = new OrderItemModel(o4, p2, 2, p2.getPrice());
		OrderItemModel oi6 = new OrderItemModel(o5, p4, 2, p4.getPrice());
        

		orderItemRepository.saveAll(Arrays.asList(oi1, oi2, oi3, oi4, oi5, oi6));
    }
}
