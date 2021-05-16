package com.javatechie.os.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javatechie.os.api.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
