package com.Lisiniarivo.Application.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.Lisiniarivo.Application.Dto.ArticleDto;

public interface ArticleService {

	Page<ArticleDto> searchArticlebyName(String name,Pageable pageable);
	ArticleDto findById(Long id);
	ArticleDto createArticle(ArticleDto article);
	ArticleDto updateArticle(Long id,ArticleDto article);
	void deleteById(Long id);
}
