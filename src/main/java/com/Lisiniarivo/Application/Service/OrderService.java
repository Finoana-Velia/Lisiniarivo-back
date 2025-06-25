package com.Lisiniarivo.Application.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.Lisiniarivo.Application.Dto.OrderRequestDto;
import com.Lisiniarivo.Application.Dto.OrderResponseDto;

public interface OrderService {
	
	Page<OrderResponseDto> searchByReference(String reference, Pageable request);
	OrderResponseDto findById(Long id);
	OrderResponseDto createOrder(OrderRequestDto orderRequest);
	OrderResponseDto updateOrder(Long id, OrderRequestDto orderRequest);
	void deleteById(Long id);

}
