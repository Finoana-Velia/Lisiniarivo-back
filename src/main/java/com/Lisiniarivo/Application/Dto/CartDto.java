package com.Lisiniarivo.Application.Dto;

import com.Lisiniarivo.Application.Entity.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
	private Long idArticle;
	private Size size;
}
