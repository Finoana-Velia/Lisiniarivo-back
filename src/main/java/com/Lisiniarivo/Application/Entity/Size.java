package com.Lisiniarivo.Application.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Size {
	//1-2-4-6-8-10-12 Enfant
	UN_AN("1"),DEUX_ANS("2"),QUATRE_ANS("4"),
	SIX_ANS("6"),HUIT_ANS("8"),DIX_ANS("10"),DOUZE_ANS("12"),
	S("S"),XS("XS"),M("M"),L("L"),XL("XL"),XXL("XXL"),XXXL("XXXL");
	private String value;
}
