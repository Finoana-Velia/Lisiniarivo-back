package com.Lisiniarivo.Application.Controller;

import org.apache.commons.io.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Lisiniarivo.Application.Dto.ArticleDto;
import com.Lisiniarivo.Application.Service.ArticleService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import static com.Lisiniarivo.Application.Core.FileManagement.registerFile;
import static com.Lisiniarivo.Application.Core.FileManagement.updateFile;
import static com.Lisiniarivo.Application.Core.FileManagement.deleteFile;
import static com.Lisiniarivo.Application.Core.FileManagement.getFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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
	
	@GetMapping("/image")
	@ResponseBody
	public byte[] getImage(Long id) throws FileNotFoundException, IOException {
		File file = getFile(id,"article");
		return IOUtils.toByteArray(new FileInputStream(file));
	}
	
	@PostMapping
	public ResponseEntity<ArticleDto> create(
			@Valid @ModelAttribute ArticleDto article,
			@RequestParam MultipartFile file
			) throws IllegalStateException, IOException {
		ArticleDto articleSaved;
		if(!file.isEmpty()) {
			article.setImage(file.getOriginalFilename());
			articleSaved = this.articleService.createArticle(article);
			registerFile(file,"article",articleSaved.getId());
		}else {
			articleSaved = this.articleService.createArticle(article);
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(articleSaved);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ArticleDto> update(
			@PathVariable Long id,
			@Valid ArticleDto article,
			@RequestParam(required = false) MultipartFile file) throws IllegalStateException, IOException {
		ArticleDto articleUpdated;
		if(file != null) {
			article.setImage(file.getOriginalFilename());
			articleUpdated = this.articleService.updateArticle(id, article);
			updateFile(file, "article" , id);
		}else {
			articleUpdated = this.articleService.updateArticle(id, article);
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(articleUpdated);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.articleService.deleteById(id);
		deleteFile(id,"article");
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	

}
