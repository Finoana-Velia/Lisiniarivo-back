package com.Lisiniarivo.Application.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.Lisiniarivo.Application.Dto.DelivererRequestDto;
import com.Lisiniarivo.Application.Dto.DelivererResponseDto;

public interface DelivererService {

	Page<DelivererResponseDto> searchDelivererByName(String name,Pageable pageable);
	DelivererResponseDto findById(Long id);
	DelivererResponseDto createDeliverer(DelivererRequestDto deliverer);
	DelivererResponseDto updateDeliverer(Long id,DelivererRequestDto deliverer);
	void deleteById(Long id);
}
