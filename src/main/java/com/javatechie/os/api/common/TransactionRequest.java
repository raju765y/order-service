package com.javatechie.os.api.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.javatechie.os.api.entity.Order;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
	private Order order;
	private Payment payment;
}
