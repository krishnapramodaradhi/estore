package com.deloitte.fi.estore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.deloitte.fi.estore.model.Cart;

@SpringBootApplication
@EnableDiscoveryClient
public class CartServiceApplication {
	
	@Bean
	public Cart cart() {
		return new Cart();
	}

	public static void main(String[] args) {
		SpringApplication.run(CartServiceApplication.class, args);
	}
}
