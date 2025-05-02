package com.Lisiniarivo.Application.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Category {
	HAUT("Haut"),BAS("Bas"),ENSEMBLE("Ensemble"),ROBE("Robe");
	
	private String value;
}
