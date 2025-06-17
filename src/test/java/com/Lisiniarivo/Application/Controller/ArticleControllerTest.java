package com.Lisiniarivo.Application.Controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import com.Lisiniarivo.Application.Entity.Article;
import com.Lisiniarivo.Application.Entity.Category;
import com.Lisiniarivo.Application.Entity.Size;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class ArticleControllerTest {
	
	String url;
	private ObjectMapper objectMapper;
	private final MockMvc mock;
	
	@Autowired
	public ArticleControllerTest(MockMvc mock) {
		this.mock = mock;
	}
	
	@BeforeEach
	void setUp() {
		this.url = "/api/v1/article";
		this.objectMapper = new ObjectMapper();
	}
	
	@Test
	void testCreateArticle() throws Exception {
		MockMultipartFile file = new MockMultipartFile(
				"file","image.jpg","application/jpeg","fake image content".getBytes());
		mock.perform(multipart(this.url)
				.file(file)
				.param("name","T-shirt pour enfant")
				.param("availability", "true")
				.param("price", "10000.00")
				.param("catrgory","HAUT")
				.param("size", "UN_AN").param("size", "DEUX_ANS")
				.param("description", "Longue description de l'article"))
		.andExpect(status().isCreated());
	}
	
	@Test
	void testUpdateArticle() throws Exception{
		MockMultipartFile file = new MockMultipartFile(
				"file","image.jpg","application/jpeg","fake image content".getBytes());
		mock.perform(multipart(this.url + "/{id}",1L)
				.file(file)
				.with(request -> {
					request.setMethod("PUT");
					return request;
				})
				.param("name","T-shirt")
				.param("availability", "true")
				.param("price", "10000.00")
				.param("catrgory","HAUT")
				.param("size", "UN_AN").param("size", "DEUX_ANS")
				.param("description", "Longue description de l'article"))
			.andExpect(status().isAccepted());
	}
	
	@Test
	void testFindById() throws Exception {
		mock.perform(get(this.url + "/{id}", 1L))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name",is("T-shirt")));
	}
	
	@Test
	void testSearchArticle() throws Exception{
		mock.perform(get(this.url)
				.param("page", "0")
				.param("size", "10"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.totalPages",is(0)))
		.andExpect(jsonPath("$.totalElements",is(0)))
		.andExpect(jsonPath("$.numberOfElements",is(0)));
	}
	
//	@Test
//	void testCreateArticleWithEmptyValue() {
//		Article article = Article.builder()
//				.name("").image("image.png")
//				.availability(true).price(10000.00)
//				.category(Category.HAUT)
//				.sizes(Set.of(Size.DEUX_ANS,Size.DIX_ANS))
//				.color(Set.of("blanc","noir"))
//				.description("Longue description de l'article")
//				.build();
//
//		
//		
//	}

}
