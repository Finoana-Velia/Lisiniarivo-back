package com.Lisiniarivo.Application.Service.Impl;

import static com.Lisiniarivo.Application.Core.EntityMapper.toDto;
import static com.Lisiniarivo.Application.Core.EntityMapper.toEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Lisiniarivo.Application.Dto.CartDto;
import com.Lisiniarivo.Application.Dto.OrderRequestDto;
import com.Lisiniarivo.Application.Dto.OrderResponseDto;
import com.Lisiniarivo.Application.Entity.Article;
import com.Lisiniarivo.Application.Entity.Cart;
import com.Lisiniarivo.Application.Entity.Deliverer;
import com.Lisiniarivo.Application.Entity.Order;
import com.Lisiniarivo.Application.Exception.ResourceNotFoundException;
import com.Lisiniarivo.Application.Repository.ArticleRepository;
import com.Lisiniarivo.Application.Repository.DelivererRepository;
import com.Lisiniarivo.Application.Repository.OrderRepository;
import com.Lisiniarivo.Application.Service.OrderService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService{

	private final OrderRepository orderRepository;
	private final ArticleRepository articleRepository;
	private final DelivererRepository delivererRepository;
	
	
	@Override
	public Page<OrderResponseDto> searchByReference(String reference, Pageable request) {
		return this.orderRepository.searchByReference("%"+reference+"%", request)
				.map(order -> toDto(order,OrderResponseDto.class));
	}

	@Override
	public OrderResponseDto findById(Long id) {
		return this.orderRepository.findById(id).map(
				order -> toDto(order,OrderResponseDto.class))
				.orElseThrow(() -> new ResourceNotFoundException(""));
	}

	@Override
	public OrderResponseDto createOrder(OrderRequestDto orderRequest) {
		Order order = toEntity(orderRequest, Order.class);
		order.setCreatedAt(LocalDateTime.now());
		order.setDeliverer(this.findDelivererById(orderRequest.getIdDeleiverer()));
//		List<Cart> orderLine = new ArrayList<>();
//		for(CartDto item : orderRequest.getCartDto()) {
//			Cart cart = Cart.builder()
//					.article(this.findArticleById(item.getIdArticle()))
//					.size(item.getSize()).build();
//			orderLine.add(cart);
//		}
//		order.setCart(orderLine);
		order.setCart(this.convertCartDto(orderRequest.getCartDto()));
		Order orderSaved = this.orderRepository.save(order);
		return toDto(orderSaved,OrderResponseDto.class);
	}

	@Override
	public OrderResponseDto updateOrder(Long id, OrderRequestDto orderRequest) {
		Order order = toEntity(orderRequest, Order.class);
		order.setId(id);
		return this.orderRepository.findById(id).map(orderFound -> {
			Order orderUpdate = orderFound;
			if(!orderFound.isDelivered()) {
				order.setCreatedAt(orderFound.getCreatedAt());
				order.setUpdatedAt(LocalDateTime.now());
				order.setDeliverer(this.findDelivererById(orderRequest.getIdDeleiverer()));
				order.setCart(this.convertCartDto(orderRequest.getCartDto()));	
				orderUpdate = this.orderRepository.save(order);
				log.info(orderUpdate.getReference() + " has been updated");
			}
			return toDto(orderUpdate, OrderResponseDto.class);
		}).orElseThrow(() -> new ResourceNotFoundException("Order " + id + " not found"));
	}

	@Override
	public void deleteById(Long id) {
		Order order = this.orderRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Order " + id + " not found"));
		log.warn("Order " + id + " will be deleted");
		this.orderRepository.deleteById(order.getId());
	}
	
	private List<Cart> convertCartDto(List<CartDto> cartDto) {
		return cartDto.stream().map(item -> Cart.builder()
				.article(this.findArticleById(item.getIdArticle()))
				.size(item.getSize()).build()).toList();	
	}
	
	private Article findArticleById(Long id) {
		return this.articleRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Article " + id + " not found"));
	}
	
	private Deliverer findDelivererById(Long id) {
		return this.delivererRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Deliverer " + id + " not found"));
	}

}
