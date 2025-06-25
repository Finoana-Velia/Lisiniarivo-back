package com.Lisiniarivo.Application.Dto;

import java.time.LocalDate;
import java.util.List;

import com.Lisiniarivo.Application.Entity.Cart;
import com.Lisiniarivo.Application.Entity.Client;
import com.Lisiniarivo.Application.Entity.Deliverer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {
	
	private Long id;
	private LocalDate deliveryDate;
	private String reference;
	private double deliveryFee;
	private double total;
	private boolean delivered;
	private Client client;
	private List<Cart> cart;
	private Deliverer deliverer;
	private String description;

}
