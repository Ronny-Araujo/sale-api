package com.github.ronnyaraujo.saleapi.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.ronnyaraujo.saleapi.model.PaymentModel;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentModel, Long> {

}
