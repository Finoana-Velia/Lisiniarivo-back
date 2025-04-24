package com.Lisiniarivo.Application.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/commandes")
public class CommandeController {
	
	
	@GetMapping
	public ResponseEntity<String> firstEndPoint() {
		return ResponseEntity.status(HttpStatus.OK).body("hello world");
	}

}
