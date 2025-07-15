package com.Lisiniarivo.Application.Service;

import static com.Lisiniarivo.Application.Core.EntityMapper.toDto;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.Lisiniarivo.Application.Dto.CartDto;
import com.Lisiniarivo.Application.Dto.OrderRequestDto;
import com.Lisiniarivo.Application.Dto.OrderResponseDto;
import com.Lisiniarivo.Application.Entity.Article;
import com.Lisiniarivo.Application.Entity.Cart;
import com.Lisiniarivo.Application.Entity.Category;
import com.Lisiniarivo.Application.Entity.Client;
import com.Lisiniarivo.Application.Entity.Deliverer;
import com.Lisiniarivo.Application.Entity.Order;
import com.Lisiniarivo.Application.Entity.Size;
import com.Lisiniarivo.Application.Exception.ResourceNotFoundException;
import com.Lisiniarivo.Application.Repository.ArticleRepository;
import com.Lisiniarivo.Application.Repository.DelivererRepository;
import com.Lisiniarivo.Application.Repository.OrderRepository;
import com.Lisiniarivo.Application.Service.Impl.OrderServiceImpl;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
	
	@Mock
	OrderRepository orderRepository;
	
	@Mock
	DelivererRepository delivererRepository;
	
	@Mock
	ArticleRepository articleRepository;
	
	@InjectMocks
	OrderServiceImpl orderService;
	
	List<Order> orders;
	PageRequest request;
	LocalDate dateUsed;
	
	@BeforeEach
	void setUp() {
		orders = generateOrder();
		request = PageRequest.of(0, 10);
		dateUsed = LocalDate.now();
	}
	
	@Test
	@DisplayName("Test search article by reference")
	void testSearchByReference() {
		String reference = "JD";
		Order order = orders.get(0);
		
		when(this.orderRepository.searchByReference("%"+reference+"%", request))
		.thenReturn(new PageImpl<>(List.of(order)));
		
		Page<OrderResponseDto> response = this.orderService.searchByReference(reference, request);
		
		assertAll(
				() -> assertNotNull(response),
				() -> assertEquals(1,response.getContent().size()),
				() -> assertEquals("John Doe",response.getContent().get(0).getClient().getName())
				);
	}
	
	@Test
	@DisplayName("Test search order by an unknown reference element")
	void testSearchWrongOrderByReference() {
		String reference = "DK/100";
		
		when(this.orderRepository.searchByReference("%"+reference+"%", request))
		.thenReturn(new PageImpl(new ArrayList<>()));
		
		Page<OrderResponseDto> response = this.orderService.searchByReference(reference, request);
		
		assertAll(
				() -> assertNotNull(response), // there are always response but an empty content
				() -> assertEquals(0,response.getContent().size())
				);
	}
	
	@Test
	@DisplayName("Test search order by an empty value reference")
	void testSearchByEmptyValue() {
		String reference = "";
		
		when(this.orderRepository.searchByReference("%"+reference+"%", request))
		.thenReturn(new PageImpl<>(orders));
		
		Page<OrderResponseDto> response = this.orderService.searchByReference(reference, request);
		
		assertAll(
				() -> assertNotNull(response),
				() -> assertEquals(2,response.getContent().size()),
				() -> assertEquals("John Doe", response.getContent().get(0).getClient().getName()),
				() -> assertEquals("CMD-20253006-MG/1",response.getContent().get(1).getReference())
				);
	}
	
	@Test
	@DisplayName("Test find order by id")
	void testFindOrderById() {
		when(this.orderRepository.findById(2L))
		.thenReturn(Optional.of(orders.get(1)));
		
		OrderResponseDto response = this.orderService.findById(2L);
		
		assertAll(
				() -> assertNotNull(response),
				() -> assertEquals("CMD-20253006-MG/1",response.getReference())
				);
	}
	
	@Test
	@DisplayName("Test order by id failed")
	void testFindByWrongId() {
		when(this.orderRepository.findById(100L)).thenReturn(Optional.empty());
		
		assertThrows(ResourceNotFoundException.class,
				() -> this.orderService.findById(100L)
				);
	}
	
	@Test
	@DisplayName("Test creating an order")
	void testCreateOrder() {
		
		Article ensemble = Article.builder().id(1L)
				.name("T-shirt").image("T-shirt.png").availability(true)
				.price(20000.00).category(Category.HAUT).sizes(Set.of(Size.DEUX_ANS,Size.M,Size.S,Size.L))
				.description("Avec DTF personnalisable").build();
		
		Deliverer deliverer = Deliverer.builder().id(1L)
				.name("Rajean").contact("+456789123").build();
		
//		
//		Order order = Order.builder().deliveryDate(dateUsed)
//		.reference("CMD-20253006-JD/1").deliveryFee(4000.00)
//		.total(24000.00).delivered(false).client(Client.builder()
//		.contact(List.of("+1234567890")).name("John Doe")
//		.deliveryAddress("234 Sunset Boulevard").build())
//		.cart(List.of(generateCart(ensemble,Size.DEUX_ANS)))
//		.deliverer(deliverer).build();
		
		Mockito.when(this.delivererRepository.findById(1L)).thenReturn(Optional.of(deliverer));
		Mockito.when(this.articleRepository.findById(1L)).thenReturn(Optional.of(ensemble));	
		
		OrderRequestDto request = OrderRequestDto.builder()
				.deliveryDate(dateUsed).deliveryFee(4000.00)
				.total(24000.00).delivered(false).client(Client.builder()
				.contact(List.of("+1234567890")).name("John Doe")
				.deliveryAddress("234 Sunset Boulevard").build())
				.cartDto(List.of(generateCartDto(1L,Size.DEUX_ANS)))
				.idDeliverer(1L)
				.build();
		
		OrderResponseDto response = this.orderService.createOrder(request);
		
//		assertAll(
//				() -> assertNotNull(response),
//				() -> assertEquals(response.getClient().getName(),request.getClient().getName()),
//				() -> assertEquals(response.getDeliverer().getId(),request.getIdDeliverer())
//				);
		
		Assertions.assertNotNull(response);
		Assertions.assertEquals
	}
	
	@Test
	@DisplayName("Test update order failed because of id")
	@Disabled
	void testUpdateFailedBecauseOfId() {
		Long id = 100L;
		
		OrderRequestDto request = OrderRequestDto.builder()
				.deliveryDate(dateUsed).deliveryFee(4000.00)
				.total(24000.00).delivered(false).client(Client.builder()
				.contact(List.of("+1234567890")).name("John Doe")
				.deliveryAddress("234 Sunset Boulevard").build())
				.cartDto(List.of(generateCartDto(1L,Size.DEUX_ANS)))
				.idDeliverer(1L)
				.build();
		
		assertThrows(ResourceNotFoundException.class,
				() -> this.orderService.updateOrder(id, request));
		
	}
	
	@Test
	@DisplayName("Test update order")
	void testUpdateOrder() {
		Order orderFound = orders.get(0);
		orderFound.setId(1L);
		
		Article robe = Article.builder()
				.name("Robe").image("robe.jpg").availability(true)
				.price(30000.00).category(Category.ROBE)
				.sizes(Set.of(Size.M,Size.S,Size.L))
				.description("Avec DTF le haut et bas").build();
		
		Deliverer deliverer = Deliverer.builder()
				.name("Rajean")
				.contact("+456789123")
				.build();
		
		Order newOrder = Order.builder()
				.deliveryDate(LocalDate.now())
				.reference("CMD-20253006-JD/1")
				.deliveryFee(4000.00)
				.total(24000.00)
				.delivered(false)
				.client(Client.builder()
						.contact(List.of("+1234567890"))
						.name("John Doe")
						.deliveryAddress("234 Sunset Boulevard")
						.build())
				.cart(List.of(
						generateCart(robe,Size.HUIT_ANS)))
				.deliverer(deliverer)
				.description("Tshirt vierge")
				.build();
		
		OrderRequestDto request = toDto(newOrder,OrderRequestDto.class);
		OrderResponseDto response = this.orderService.updateOrder(1L, request);
		
		assertAll(
				() -> assertNotNull(response),
				() -> assertEquals("John Doe", response.getClient().getName()),
				() -> assertEquals(1,response.getCart().size())
				);
		
	}
	
	List<Order> generateOrder() {
		Article ensemble = Article.builder()
				.name("T-shirt").image("T-shirt.png").availability(true)
				.price(20000.00).category(Category.HAUT)
				.sizes(Set.of(Size.DEUX_ANS,Size.M,Size.S,Size.L))
				.description("Avec DTF personnalisable").build();
		Article tshirt = Article.builder()
				.name("Ensemble").image("Ensemble.jpg").availability(true)
				.price(30000.00).category(Category.ENSEMBLE)
				.sizes(Set.of(Size.DEUX_ANS,Size.M,Size.S,Size.L))
				.description("Avec DTF le haut et bas").build();
		Article robe = Article.builder()
				.name("Robe").image("robe.jpg").availability(true)
				.price(30000.00).category(Category.ROBE)
				.sizes(Set.of(Size.M,Size.S,Size.L))
				.description("Avec DTF le haut et bas").build();
		Deliverer deliverer = Deliverer.builder()
				.name("Rajean")
				.contact("+456789123")
				.build();
		return List.of(
				Order.builder()
				.deliveryDate(LocalDate.now())
				.reference("CMD-20253006-JD/1")
				.deliveryFee(4000.00)
				.total(24000.00)
				.delivered(false)
				.client(Client.builder()
						.contact(List.of("+1234567890"))
						.name("John Doe")
						.deliveryAddress("234 Sunset Boulevard")
						.build())
				.cart(List.of(
						generateCart(ensemble,Size.HUIT_ANS), generateCart(tshirt,Size.M)))
				.deliverer(deliverer)
				.description("Tshirt vierge")
				.build(),
				Order.builder()
				.deliveryDate(LocalDate.now())
				.reference("CMD-20253006-MG/1")
				.deliveryFee(3000.00)
				.total(28000.00)
				.delivered(false)
				.client(Client.builder()
						.contact(List.of("+9876543210"))
						.name("Margarett Goodman")
						.deliveryAddress("109 Garden Road")
						.build())
				.cart(List.of(
						generateCart(robe,Size.M)))
				.deliverer(deliverer)
				.build()
				);
		
	}
	
	Cart generateCart(Article article, Size size) {
		return Cart.builder().article(article).size(size).build();
	}
	
	CartDto generateCartDto(Long idArticle,Size size) {
		return CartDto.builder().idArticle(idArticle).size(size).build();
	}

}