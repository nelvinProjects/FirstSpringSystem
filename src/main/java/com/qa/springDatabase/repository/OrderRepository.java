package com.qa.springDatabase.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.qa.springDatabase.model.Order;

public interface  OrderRepository extends JpaRepository<Order, Long> {
	Page<Order> findByPersonId(Long personId, Pageable pageable);
}
