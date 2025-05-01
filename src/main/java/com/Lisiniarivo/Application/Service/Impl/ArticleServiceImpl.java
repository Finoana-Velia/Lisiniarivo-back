package com.Lisiniarivo.Application.Service.Impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Lisiniarivo.Application.Dto.ArticleDto;
import com.Lisiniarivo.Application.Entity.Article;
import com.Lisiniarivo.Application.Exception.ResourceNotFoundException;
import com.Lisiniarivo.Application.Repository.ArticleRepository;
import com.Lisiniarivo.Application.Service.ArticleService;

import lombok.AllArgsConstructor;

import static com.Lisiniarivo.Application.Core.EntityMapper.*;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ArticleServiceImpl implements ArticleService{
	
	private final ArticleRepository articleRepository;

	@Override
	public Page<ArticleDto> searchArticlebyName(String name, Pageable pageable) {
		return this.articleRepository.searchArticleByName("%" + name + "%", pageable)
				.map(article -> toDto(article,ArticleDto.class));
	}

	@Override
	public ArticleDto findById(Long id) {
		return this.articleRepository.findById(id)
				.map(article -> toDto(article,ArticleDto.class))
				.orElseThrow(() -> new ResourceNotFoundException("Article : " + id + " not found"));
	}

	@Override
	public ArticleDto createArticle(ArticleDto article) {
		Article articleEntity = toEntity(article,Article.class);
		articleEntity.setCreatedAt(LocalDateTime.now());
		Article articleSaved = this.articleRepository.save(articleEntity);
		return toDto(articleSaved,ArticleDto.class);
	}

	@Override
	public ArticleDto updateArticle(Long id, ArticleDto article) {
		Article articleEntity = toEntity(article,Article.class);
		articleEntity.setId(id);
		return this.articleRepository.findById(id)
				.map(articleFound -> {
					if(articleEntity.getImage() == null) {
						articleEntity.setImage(articleFound.getImage());
					}
					articleEntity.setCreatedAt(articleFound.getCreatedAt());
					articleEntity.setUpdatedAt(LocalDateTime.now());
					Article articleUpdated = this.articleRepository.save(articleEntity);
					return toDto(articleUpdated,ArticleDto.class);
				})
				.orElseThrow(() -> new ResourceNotFoundException("Article : " + id + " not found"));
	}

	@Override
	public void deleteById(Long id) {
		Article articleFound = this.articleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Article : " + id + " not found"));
		this.articleRepository.deleteById(articleFound.getId());
		
	}

}
