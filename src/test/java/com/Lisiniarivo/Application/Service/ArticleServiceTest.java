package com.Lisiniarivo.Application.Service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.Lisiniarivo.Application.Dto.ArticleDto;
import com.Lisiniarivo.Application.Entity.Article;
import com.Lisiniarivo.Application.Entity.Category;
import com.Lisiniarivo.Application.Entity.Size;
import com.Lisiniarivo.Application.Exception.ResourceNotFoundException;
import com.Lisiniarivo.Application.Repository.ArticleRepository;
import com.Lisiniarivo.Application.Service.Impl.ArticleServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {
	
	@Mock
	ArticleRepository articleRepository;
	
	@InjectMocks
	ArticleServiceImpl articleService;
	
	List<Article> articles;
	PageRequest request;
	
	@BeforeEach
	void setUp() {
		articles = generateArticle();
		request = PageRequest.of(0, 10);
	}
	
	@Test
	@DisplayName("Test search article by name")
	void testSearchByName() {
		String name = "Robe";
		Article article = articles.get(2);
		
		when(this.articleRepository.searchArticleByName("%" + name + "%", request))
		.thenReturn(new PageImpl<>(List.of(article)));
		
		Page<ArticleDto> response = this.articleService.searchArticlebyName(name, request);
 		
		assertAll(
				() -> assertNotNull(response),
				() -> assertEquals(1,response.getContent().size()),
				() -> assertEquals("Robe",response.getContent().get(0).getName()),
				() -> assertEquals(Category.ROBE,response.getContent().get(0).getCategory())
				);
	}
	
	
	@Test
	@DisplayName("Test find all article")
	void testFindAllArticle() {
	
		when(this.articleRepository.searchArticleByName("%%", request))
		.thenReturn(new PageImpl<>(articles));
		
		Page<ArticleDto> response = this.articleService.searchArticlebyName("", request);
		assertAll(
				() -> assertNotNull(response),
				() -> assertEquals(3,response.getContent().size()),
				() -> assertEquals("T-shirt",response.getContent().get(0).getName()),
				() -> assertEquals(Category.ROBE,response.getContent().get(2).getCategory())
				);
	}
	
	@Test
	@DisplayName("Test find article by id")
	void testFindById() {
		Long id = 1L;
		Article article = articles.get(1);
		
		when(this.articleRepository.findById(id))
		.thenReturn(Optional.of(article));
		
		ArticleDto response = this.articleService.findById(id);
		
		assertAll(
				() -> assertNotNull(response),
				() -> assertEquals("Ensemble",response.getName()),
				() -> assertEquals(30000.00,response.getPrice()),
				() -> assertEquals(Category.ENSEMBLE,response.getCategory())
				);
	}
	
	@Test
	@DisplayName("Test find article with wrong id")
	void testWithWrongId() {
		Long id = 300L;
		
		when(this.articleRepository.findById(id))
		.thenReturn(Optional.empty());
		
		assertThrows(ResourceNotFoundException.class,
				() -> this.articleService.findById(id)
				);
	}
	
	List<Article> generateArticle() {
		return List.of(
				Article.builder()
				.name("T-shirt").image("T-shirt.png").availability(true)
				.price(20000.00).category(Category.HAUT)
				.color(Set.of("Blue","Black","Green","Red","White"))
				.sizes(Set.of(Size.DEUX_ANS,Size.M,Size.S,Size.L))
				.description("Avec DTF personnalisable")
				.build(),
				Article.builder()
				.name("Ensemble").image("Ensemble.jpg").availability(true)
				.price(30000.00).category(Category.ENSEMBLE)
				.color(Set.of("Pink","white","green"))
				.sizes(Set.of(Size.DEUX_ANS,Size.M,Size.S,Size.L))
				.description("Avec DTF le haut et bas")
				.build(),
				Article.builder()
				.name("Robe").image("robe.jpg").availability(true)
				.price(30000.00).category(Category.ROBE)
				.color(Set.of("Pink","white","green"))
				.sizes(Set.of(Size.M,Size.S,Size.L))
				.description("Avec DTF le haut et bas")
				.build()
				);
	}
	
}
