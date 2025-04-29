package com.Lisiniarivo.Application.Service.Impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Lisiniarivo.Application.Dto.DelivererRequestDto;
import com.Lisiniarivo.Application.Dto.DelivererResponseDto;
import com.Lisiniarivo.Application.Entity.Deliverer;
import com.Lisiniarivo.Application.Exception.ResourceNotFoundException;
import com.Lisiniarivo.Application.Repository.DelivererRepository;
import com.Lisiniarivo.Application.Service.DelivererService;

import lombok.AllArgsConstructor;

import static com.Lisiniarivo.Application.Core.EntityMapper.*;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class DelivererServiceImpl implements DelivererService{
	
	private final DelivererRepository delivererRepository;

	@Override
	public Page<DelivererResponseDto> searchDelivererByName(String name, Pageable pageable) {
		return this.delivererRepository.searchDelivererByName("%" + name + "%", pageable)
				.map(deliverer -> toDto(deliverer,DelivererResponseDto.class));
	}

	@Override
	public DelivererResponseDto findById(Long id) {
		return this.delivererRepository.findById(id)
				.map(deliverer -> toDto(deliverer,DelivererResponseDto.class))
				.orElseThrow(
						() -> new ResourceNotFoundException("Deliverer : " + id + " not found")
						);
	}

	@Override
	public DelivererResponseDto createDeliverer(DelivererRequestDto deliverer) {
		Deliverer delivererEntity = toEntity(deliverer, Deliverer.class);
		delivererEntity.setCreatedAt(LocalDateTime.now());
		Deliverer delivererSaved = this.delivererRepository.save(delivererEntity);
		return toDto(delivererSaved,DelivererResponseDto.class);
	}

	@Override
	public DelivererResponseDto updateDeliverer(Long id, DelivererRequestDto deliverer) {
		Deliverer delivererEntity = toEntity(deliverer,Deliverer.class);
		delivererEntity.setUpdatedAt(LocalDateTime.now());
		delivererEntity.setId(id);
		return this.delivererRepository.findById(id)
				.map(delivererFound -> {
					Deliverer delivererUpdated = this.delivererRepository.save(delivererEntity);
					return toDto(delivererUpdated,DelivererResponseDto.class);
				}).orElseThrow(
						() -> new ResourceNotFoundException("Deliverer : " + id + " not found")
						);
	}

	@Override
	public void deleteById(Long id) {
		Deliverer delivererFound = this.delivererRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Deliverer : " + id + " not found")
				);
		this.delivererRepository.deleteById(delivererFound.getId());
	}

}
