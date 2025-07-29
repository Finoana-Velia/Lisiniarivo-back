package com.Lisiniarivo.Application.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.Lisiniarivo.Application.Dto.OrderRequestDto;
import com.Lisiniarivo.Application.Dto.OrderResponseDto;
import com.Lisiniarivo.Application.Entity.Article;
import com.Lisiniarivo.Application.Entity.Cart;
import com.Lisiniarivo.Application.Entity.Category;
import com.Lisiniarivo.Application.Entity.Client;
import com.Lisiniarivo.Application.Entity.Deliverer;
import com.Lisiniarivo.Application.Entity.Order;
import com.Lisiniarivo.Application.Entity.Size;
import com.Lisiniarivo.Application.Repository.ArticleRepository;
import com.Lisiniarivo.Application.Repository.DelivererRepository;
import com.Lisiniarivo.Application.Service.ArticleService;
import com.Lisiniarivo.Application.Service.DelivererService;
import com.Lisiniarivo.Application.Service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {
	
	private MockMvc mockMvc;
	private ObjectMapper objectMapper;
	
	@MockBean
	private OrderService orderService;
	
	@MockBean
	private DelivererRepository delivererRepository;
	
	@MockBean
	private ArticleRepository articleRepository;
	
	private Deliverer delivererSaved;
	private Article articleSaved;
	
	public OrderControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
		this.mockMvc = mockMvc;
		this.objectMapper = objectMapper;
	}
	
	@BeforeEach
	void setUp() {
		delivererSaved = Deliverer.builder().id(1L).name("deliverer").contact("+1234567890").build();
		articleSaved = Article.builder().id(1L).name("T-shirt").image("Tshirt.png")
				.availability(true).price(20000.00).category(Category.HAUT).sizes(Set.of(Size.L)).build();
	}
	
	
	private Cart generateCart(Article article, Size size) {
		return Cart.builder().article(article).size(size).build();
	}

}
