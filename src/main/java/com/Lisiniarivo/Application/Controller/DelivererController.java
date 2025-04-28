package com.Lisiniarivo.Application.Controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Lisiniarivo.Application.Dto.DelivererRequestDto;
import com.Lisiniarivo.Application.Dto.DelivererResponseDto;
import com.Lisiniarivo.Application.Service.DelivererService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/deliverer")
@AllArgsConstructor
public class DelivererController {
	
	private DelivererService delivererService;
	
	@GetMapping
	public ResponseEntity<Page<DelivererResponseDto>> searchDelivererByName(
			@RequestParam(defaultValue="") String name,
			@RequestParam(defaultValue="0")int page,
			@RequestParam(defaultValue="0")int size
			) {
		PageRequest request = PageRequest.of(page, size != 0 ? size : Integer.MAX_VALUE);
		Page<DelivererResponseDto> deliverers = this.delivererService.searchDelivererByName(name, request);
		return ResponseEntity.status(HttpStatus.OK).body(deliverers);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DelivererResponseDto> findById(@PathVariable Long id) {
		DelivererResponseDto deliverer = this.delivererService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(deliverer);
	}
	
	@PostMapping
	public ResponseEntity<DelivererResponseDto> create(@RequestBody DelivererRequestDto deliverer) {
		DelivererResponseDto delivererSaved = this.delivererService.createDeliverer(deliverer);
		return ResponseEntity.status(HttpStatus.CREATED).body(delivererSaved);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<DelivererResponseDto> update(@PathVariable Long id,@RequestBody DelivererRequestDto deliverer) {
		DelivererResponseDto delivererUpdated = this.delivererService.updateDeliverer(id, deliverer);
		return ResponseEntity.status(HttpStatus.OK).body(delivererUpdated);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.delivererService.deleteById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
