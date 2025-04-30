package com.Lisiniarivo.Application.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Size {
	ENFANT("Enfant"),S("S"),XS("XS"),M("M"),L("L"),XL("XL"),XXL("XXL");
	private String value;
}
