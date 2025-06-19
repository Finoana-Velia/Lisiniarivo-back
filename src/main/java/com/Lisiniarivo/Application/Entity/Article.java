package com.Lisiniarivo.Application.Entity;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	@NotNull(message = "Article's name should not be null")
	@NotBlank(message = "Article should have a name")
	private String name;
	private String image;
	private boolean availability;
	
	@NotNull(message = "Price should not be null")
	private double price;
	
	@Enumerated(EnumType.STRING)
	private Category category;
	
	@ElementCollection
	@Enumerated(EnumType.STRING)
	private Set<Size> sizes;
	
//	@Lob
	@Column(columnDefinition = "TEXT") // For postgresql IOD -> TEXT
	private String description;

}
