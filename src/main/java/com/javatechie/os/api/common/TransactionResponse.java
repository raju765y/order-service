package com.javatechie.os.api.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.javatechie.os.api.entity.Order;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
	private Order order;
	private double amount;
	private String transactionId; 
	private String message;
}
