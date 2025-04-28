package com.Lisiniarivo.Application.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DelivererResponseDto {
	
	private Long id;
	private String name;
	private String contact;

}
