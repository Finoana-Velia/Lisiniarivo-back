package com.Lisiniarivo.Application.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Lisiniarivo.Application.Entity.Article;

import jakarta.transaction.Transactional;

public interface ArticleRepository extends JpaRepository<Article,Long>{

	@Query("select a from Article a where a.name like :x")
	Page<Article> searchArticleByName(@Param("x")String name,Pageable pageable);
}
