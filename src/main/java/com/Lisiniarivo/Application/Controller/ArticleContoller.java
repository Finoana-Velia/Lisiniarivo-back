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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Lisiniarivo.Application.Dto.ArticleDto;
import com.Lisiniarivo.Application.Service.ArticleService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/article")
@AllArgsConstructor
public class ArticleContoller {
	
	private final ArticleService articleService;
	
	@GetMapping
	public ResponseEntity<Page<ArticleDto>> searchArticle(
			@RequestParam(defaultValue="")String name,
			@RequestParam(defaultValue="0") int page,
			@RequestParam(defaultValue="0") int size
			) {
		PageRequest request = PageRequest.of(page, size);
		Page<ArticleDto> articles = this.articleService.searchArticlebyName(name, request);
		return ResponseEntity.status(HttpStatus.OK).body(articles);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ArticleDto> findById(@PathVariable Long id) {
		ArticleDto article = this.articleService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(article);
	}
	
	@PostMapping
	public ResponseEntity<ArticleDto> create(
			@Valid ArticleDto article,
			@RequestParam MultipartFile file
			) {
		ArticleDto articleSaved = this.articleService.createArticle(article);
		return ResponseEntity.status(HttpStatus.CREATED).body(articleSaved);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ArticleDto> update(
			@PathVariable Long id,
			@Valid ArticleDto article,
			@RequestParam(required = false) MultipartFile file) {
		ArticleDto articleUpdated = this.articleService.updateArticle(id, article);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(articleUpdated);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.articleService.deleteById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	

}
