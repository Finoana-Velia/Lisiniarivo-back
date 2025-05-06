package com.Lisiniarivo.Application.Controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.net.ssl.SSLEngineResult.Status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.Lisiniarivo.Application.Dto.DelivererRequestDto;
import com.Lisiniarivo.Application.Entity.Deliverer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class DelivererContollerTest {
	
	@Autowired
	MockMvc mock;
	
	String url;
	private ObjectMapper objectMapper;
	
	@BeforeEach
	void setUp() {
		this.url = "/api/v1/deliverer";
		this.objectMapper = new ObjectMapper();
	}
	
	@Test
	@DisplayName("Test search deliverer from name with parameters")
	void testSearchDelivererWithParameters() throws Exception{
		mock.perform(get(this.url)
				.param("page", "0")
				.param("size", "10")
				.param("name", "John")
				)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.totalPages",is(1)))
		.andExpect(jsonPath("$.totalElements",is(1)))
		.andExpect(jsonPath("$.numberOfElements",is(1)));
		
	}
	
	@Test
	@DisplayName("Test search deliverer with wrong parameter")
	void testWithWrongParameter() throws Exception{
		mock.perform(get(this.url).param("page", "a"))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.statusCode",is(400)))
		.andExpect(jsonPath("$.errorType",is("INPUT_MISMATCH")));
	}
	
	@Test
	@DisplayName("Test search deliverer from name")
	void testSearchDelivererByName() throws Exception {
		mock.perform(get(this.url))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.numberOfElements",is(3)));
	}
	
	@Test
	@DisplayName("Test find deliverer by id")
	void testFindById() throws Exception {
		mock.perform(get("/api/v1/deliverer/1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.name",is("Emilson")));
	}
	
	@Test
	@DisplayName("Test find deliverer with an unknow id")
	void testFindByUnknownId() throws Exception{
		mock.perform(get("/api/v1/deliverer/499"))
		.andExpect(status().isNotFound())
		.andExpect(jsonPath("$.errorType",is("RESOURCE_NOT_FOUND")));
	}

	
	@Test
	@DisplayName("Test create deliverer succed")
	void testCreateDeliverer() throws Exception {
		Deliverer deliverer = Deliverer.builder()
				.name("Faliana")
				.contact("+4567891230")
				.build();
		
		mock.perform(post(this.url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsBytes(deliverer))
				)
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.id",is(3)))
		.andExpect(jsonPath("$.name",is("Faliana")));
	}
	
	@Test
	@DisplayName("test create deliverer failed")
	void testCreateDelivererFailed() throws Exception{
		Deliverer testContactNull = Deliverer.builder()
				.name("test")
				.contact(null)
				.build();
		/*Bad request response*/
		mock.perform(post(this.url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsBytes(testContactNull)))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.statusCode",is(400)))
		.andExpect(jsonPath("$.errorType",is("INPUT_MISMATCH")));
	}
	
	@Test
	@DisplayName("Test create deliverer failed 2")
	void testCreateDelivererWithAnEmptyFieldValue() throws Exception{
		Deliverer testNameEmpty = Deliverer.builder()
				.name("")
				.contact("+0457891569")
				.build();
		
		mock.perform(post(this.url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsBytes(testNameEmpty)))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.statusCode",is(400)))
		.andExpect(jsonPath("$.errorType",is("INPUT_MISMATCH")));
	}
	
	@Test
	@DisplayName("Test update deliverer success")
	void testUpdateDeliverer() throws Exception{
		Deliverer delivererUpdate = Deliverer.builder()
				.name("John Doe")
				.contact("+1235467890")
				.build();
		mock.perform(put("/api/v1/deliverer/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsBytes(delivererUpdate)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name",is("John Doe")))
		.andExpect(jsonPath("$.id",is(1)));
	}
	
	@Test
	@DisplayName("Test update deliverer not found")
	void testUpdateNotFound() throws Exception{
		Deliverer deliverer = Deliverer.builder()
				.name("test")
				.contact("test")
				.build();
		mock.perform(put("/api/v1/deliverer/400")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsBytes(deliverer)))
		.andExpect(status().isNotFound())
		.andExpect(jsonPath("$.statusCode",is(404)))
		.andExpect(jsonPath("$.errorType",is("RESOURCE_NOT_FOUND")));
	}
	
	@Test
	@DisplayName("Test update failed due to null value")
	void testUpdateWithNullValue() throws JsonProcessingException, Exception {
		Deliverer deliverer = Deliverer.builder()
				.name(null)
				.contact("1234567890")
				.build();
		
		mock.perform(put("/api/v1/deliverer/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsBytes(deliverer)))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.statusCode",is(400)))
		.andExpect(jsonPath("$.errorType",is("INPUT_MISMATCH")));
		
	}
	
	@Test
	@DisplayName("Test update failed due to empty value")
	void testUpdateWithEmptyValue() throws JsonProcessingException, Exception {
		Deliverer deliverer = Deliverer.builder()
				.name("")
				.contact("1234567890")
				.build();
		
		mock.perform(put("/api/v1/deliverer/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsBytes(deliverer)))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.statusCode",is(400)))
		.andExpect(jsonPath("$.errorType",is("INPUT_MISMATCH")));
		
	}
	
	@Test
	@DisplayName("Test delete deliverer success")
	void testDeleteDeliverer() throws Exception{
		mock.perform(delete("/api/v1/deliverer/3"))
		.andExpect(status().isNoContent());
	}
	
	@Test
	@DisplayName("Test delete deliverer failed")
	void testDeleteDelivererFailed() throws Exception{
		mock.perform(delete("/api/v1/deliverer/400"))
		.andExpect(status().isNotFound())
		.andExpect(jsonPath("$.errorType",is("RESOURCE_NOT_FOUND")));
	}
//	
//	@Test
//	@DisplayName("Test create a new deliverer with right informations")
//	void testRightInfoDelivererCreation() {
//		DelivererRequestDto request = DelivererRequestDto.builder()
//				.name("Faliana")
//				.contact("0123456789")
//				.build();
//		
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
