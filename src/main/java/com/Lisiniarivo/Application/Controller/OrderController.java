package com.Lisiniarivo.Application.Controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Lisiniarivo.Application.Dto.OrderRequestDto;
import com.Lisiniarivo.Application.Dto.OrderResponseDto;
import com.Lisiniarivo.Application.Service.OrderService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
public class OrderController {

	private final OrderService orderService;
	
	@GetMapping
	public ResponseEntity<Page<OrderResponseDto>> searchByReference(
			@RequestParam(defaultValue="")String reference,
			@RequestParam(defaultValue="0")int page,
			@RequestParam(defaultValue="10")int size
			) {
		PageRequest request = PageRequest.of(page, size != 0 ? size : Integer.MAX_VALUE);
		Page<OrderResponseDto> orders = this.orderService.searchByReference(reference, request);
		return ResponseEntity.status(HttpStatus.OK).body(orders);
	}
	
	@PostMapping
	public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody OrderRequestDto order) {
		OrderResponseDto orderSaved = this.orderService.createOrder(order);
		return ResponseEntity.status(HttpStatus.CREATED).body(orderSaved);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<OrderResponseDto> updateOrder(@PathVariable Long id,@RequestBody OrderRequestDto order){
		OrderResponseDto orderUpdated = this.orderService.updateOrder(id, order);
		return ResponseEntity.status(HttpStatus.OK).body(orderUpdated);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.orderService.deleteById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<OrderResponseDto> findById(@PathVariable Long id) {
		OrderResponseDto order = this.orderService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(order);
	}
	
}
