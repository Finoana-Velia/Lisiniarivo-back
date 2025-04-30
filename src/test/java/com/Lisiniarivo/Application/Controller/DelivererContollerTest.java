package com.Lisiniarivo.Application.Controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.Lisiniarivo.Application.Dto.DelivererRequestDto;

@SpringBootTest
@AutoConfigureMockMvc
public class DelivererContollerTest {
	
	@Autowired
	MockMvc mock;
	
	String url;
	
	@BeforeEach
	void setUp() {
		this.url = "/api/v1/deliverer";
	}
	
	@Test
	@DisplayName("Test create a new deliverer with right informations")
	void testRightInfoDelivererCreation() {
		DelivererRequestDto request = DelivererRequestDto.builder()
				.name("Faliana")
				.contact("0123456789")
				.build();
		
//		mock.perform(post(this.url))
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
