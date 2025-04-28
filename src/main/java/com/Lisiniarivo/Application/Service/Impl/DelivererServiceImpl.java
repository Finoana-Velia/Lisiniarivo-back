package com.Lisiniarivo.Application.Service.Impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Lisiniarivo.Application.Dto.DelivererRequestDto;
import com.Lisiniarivo.Application.Dto.DelivererResponseDto;
import com.Lisiniarivo.Application.Repository.DelivererRepository;
import com.Lisiniarivo.Application.Service.DelivererService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DelivererServiceImpl implements DelivererService{
	
	private final DelivererRepository delivererRepository;

	@Override
	public Page<DelivererResponseDto> searchDelivererByName(String name, Pageable pageable) {
		return null;
	}

	@Override
	public DelivererResponseDto findById(Long id) {
		return null;
	}

	@Override
	public DelivererResponseDto createDeliverer(DelivererRequestDto deliverer) {
		return null;
	}

	@Override
	public DelivererResponseDto updateDeliverer(Long id, DelivererRequestDto deliverer) {
		return null;
	}

	@Override
	public void deleteById(Long id) {
		
	}

}
