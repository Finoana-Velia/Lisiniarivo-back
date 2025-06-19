package com.Lisiniarivo.Application.Dto;

import java.util.Set;

import com.Lisiniarivo.Application.Entity.Category;
import com.Lisiniarivo.Application.Entity.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {
	
	private Long id;
	private String name;
	private String image;
	private boolean availability;
	private double price;
	private Category category;
	private Set<Size> sizes;
	private String description;
	
}
