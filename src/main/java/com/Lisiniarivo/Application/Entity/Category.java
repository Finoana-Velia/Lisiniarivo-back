package com.Lisiniarivo.Application.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Category {
	T_SHIRT("T-shirt"),SHORT("Short"),ENSEMBLE("Ensemble"),ROBE("Robe");
	
	private String value;
}
