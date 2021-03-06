package com.javatechie.os.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatechie.os.api.common.Payment;
import com.javatechie.os.api.common.TransactionRequest;
import com.javatechie.os.api.common.TransactionResponse;
import com.javatechie.os.api.entity.Order;
import com.javatechie.os.api.repository.OrderRepository;

@Service
@RefreshScope
public class OrderService {
	@Autowired
	private OrderRepository repository;
	
	@Autowired
	@Lazy
	private RestTemplate restTemplate;
	
	@Value("${microservice.payment-service.endpoints.endpoint.uri}")
	private String ENDPOINT_URL;
	
	private Logger log=LoggerFactory.getLogger(OrderService.class);
	public TransactionResponse saveOrder(TransactionRequest request) throws JsonProcessingException{
		String response="";
		Order order = request.getOrder();
		Payment payment = request.getPayment();
		payment.setOrderId(order.getId());
		payment.setAmount(order.getPrice());
		log.info("Order Service request : {}", new ObjectMapper().writeValueAsString(request));
		//Rest call
		Payment paymentResponse = restTemplate.postForObject(ENDPOINT_URL, payment, Payment.class);
		log.info("Payment service response from Order Service rest call : {}", new ObjectMapper().writeValueAsString(paymentResponse));
		response = paymentResponse.getPaymentStatus().equals("success") ? "Payment processing successful and order placed" : "There is a failure in payment API, order added to cart";
		
		repository.save(order);
		return new TransactionResponse(order,paymentResponse.getAmount(),paymentResponse.getTransactionId(),response);
	}
}
