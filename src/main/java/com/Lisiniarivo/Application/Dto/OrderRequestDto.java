package com.Lisiniarivo.Application.Dto;

import java.time.LocalDate;
import java.util.List;

import com.Lisiniarivo.Application.Entity.Client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
	
	private Long id;
	private LocalDate deliveryDate;
	private double deliveryFee;
	private double total;
	private boolean delivered;
	private Client client;
	private List<CartDto> cartDto;
	private Long idDeleiverer;
	private String description;

}
