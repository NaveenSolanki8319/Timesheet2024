package com.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.entities.PaymentHistory;


public interface MyOrderRepo extends JpaRepository<PaymentHistory, Long> {
	
	PaymentHistory findByrazorPayOrderId(String razorPayOrderId);

}
