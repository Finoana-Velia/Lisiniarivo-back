package com.Lisiniarivo.Application.Core;

import java.time.LocalDateTime;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Builder;

@Builder
public record ErrorResponse(
		int statusCode,
		ErrorType errorType,
		Map<String, String> details,
		String suggestion,
		LocalDateTime timeStamp
		) {
	
//	public String toString() {
//		ObjectMapper mapper = new ObjectMapper();
//		return """
//				{
//					"statusCode" : "",
//					"errorType" : "",
//					"details" : "";
//					"suggestion" : "",
//					"timeStamp" : ""
//				}
//				""";
//	}

}
