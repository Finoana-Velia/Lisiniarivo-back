package com.Lisiniarivo.Application.Entity;

import java.util.List;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Client {
	
	@NotNull(message="Client must have name")
	@NotBlank(message="Client's name should not be blank")
	private String name;
	
	@NotNull(message="Contact is mendatory")
	private List<String> contact;
	
	@NotNull(message="Delivery address is mendatory")
	@NotBlank(message="Delivery address should not be blank")
	private String deliveryAddress;

}
