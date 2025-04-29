package com.Lisiniarivo.Application.Service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static com.Lisiniarivo.Application.Core.EntityMapper.toDto;
import static com.Lisiniarivo.Application.Core.EntityMapper.toEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.Lisiniarivo.Application.Dto.DelivererRequestDto;
import com.Lisiniarivo.Application.Dto.DelivererResponseDto;
import com.Lisiniarivo.Application.Entity.Deliverer;
import com.Lisiniarivo.Application.Exception.ResourceNotFoundException;
import com.Lisiniarivo.Application.Repository.DelivererRepository;
import com.Lisiniarivo.Application.Service.Impl.DelivererServiceImpl;

import jakarta.validation.ConstraintViolationException;

@ExtendWith(MockitoExtension.class)
public class DelivererServiceTest {
	
	@Mock
	DelivererRepository delivererRepository;
	
	@InjectMocks
	DelivererServiceImpl delivererService;
	
	List<Deliverer> deliverers;
	PageRequest request;
	
	@BeforeEach
	void setUp() {
		deliverers = generateDeliverer();
		request = PageRequest.of(0, 10);
	}
	
	@Test
	@DisplayName("Test search deliverer by name")
	void testSearchByName() {
		String name = "Vic";
		Deliverer deliverer = deliverers.get(1);
		
		when(this.delivererRepository.searchDelivererByName("%" + name + "%", request))
		.thenReturn(new PageImpl<>(List.of(deliverer)));
		
		Page<DelivererResponseDto> response = this.delivererService.searchDelivererByName(name, request);
		
		assertAll(
				() -> assertNotNull(response),
				() -> assertEquals(1,response.getContent().size()),
				() -> assertEquals("Victor",response.getContent().get(0).getName())
				);
	}
	
	@Test
	@DisplayName("Test search deliverer with an empty name value")
	void testEmptyNameValue() {
		when(this.delivererRepository.searchDelivererByName("%%", request))
		.thenReturn(new PageImpl<>(deliverers));
		
		Page<DelivererResponseDto> response = this.delivererService.searchDelivererByName("", request);
		
		assertAll(
				() -> assertNotNull(response),
				() -> assertEquals(3,response.getContent().size()),
				() -> assertEquals("Emilson",response.getContent().get(0).getName()),
				() -> assertEquals("0564321987",response.getContent().get(2).getContact())
				);
	}
	
	@Test
	@DisplayName("Test deliverer by id")
	void testFindById() {
		Long id = 1L;
	
		when(this.delivererRepository.findById(id))
		.thenReturn(Optional.of(deliverers.get(0)));
		
		DelivererResponseDto response = this.delivererService.findById(id);
		
		assertAll(
				() -> assertNotNull(response),
				() -> assertEquals("Emilson",response.getName())
				);
	}
	
	@Test
	@DisplayName("Test create deliverer succed, respect all validation")
	void testCreateDeliverer() {
		Deliverer request = Deliverer.builder()
				.name("Faliana")
				.contact("1234567890")
				.build();
		
		when(this.delivererRepository.save(request)).thenReturn(request);
		
		DelivererRequestDto requestSaved = toDto(request,DelivererRequestDto.class);
		DelivererResponseDto response = this.delivererService.createDeliverer(requestSaved);
		
		
	
		assertAll(
				() -> assertNotNull(response),
				() -> assertEquals(request.getName(),response.getName())
				);
	}
	
	@Test
	@DisplayName("Test create deliverer failed")
	void testCreateDelivererFailed() {
		DelivererRequestDto delivererCreated = DelivererRequestDto.builder()
				.name("")
				.contact("1234567890")
				.build();
		assertThrows(IllegalArgumentException.class, 
				() -> this.delivererService.createDeliverer(delivererCreated)
				);
	}
	
	@Test
	@DisplayName("Test update deliverer failed because id not found")
	void testUpdateFailedById() {
		Long id = 100L;
		DelivererRequestDto delivererUpdated = DelivererRequestDto.builder()
				.name("Dominique")
				.contact("0123456789")
				.build();
		assertThrows(ResourceNotFoundException.class,
				() -> this.delivererService.updateDeliverer(id, delivererUpdated)
				);
	}
	
	@Test
	@DisplayName("Test update deliverer failed because of violation validation")
	void testUpdateFailedByField() {
		Long id = 1L;
		DelivererRequestDto request = DelivererRequestDto.builder()
				.name("Dominique")
				.contact(null)
				.build();
		assertThrows(ResourceNotFoundException.class,
				() -> this.delivererService.updateDeliverer(id, request)
				);
	}
	
	@Test
	@DisplayName("Test update deliverer with correct informations")
	void testCorrectUpdate() {
		Deliverer delivererToUpdate = deliverers.get(0);
		delivererToUpdate.setId(1L);
		
		Deliverer delivererWithNewValue = Deliverer.builder()
				.name("Dominique")
				.contact("0123456789")
				.build();
		
		delivererWithNewValue.setId(1L);
		
		when(this.delivererRepository.findById(1L))
		.thenReturn(Optional.of(delivererToUpdate));
		
		when(this.delivererRepository.save(delivererWithNewValue))
		.thenReturn(delivererWithNewValue);
		
		DelivererRequestDto request = toDto(delivererWithNewValue,DelivererRequestDto.class);
		DelivererResponseDto deliverer = this.delivererService.updateDeliverer(1L, request);
		assertAll(
				() -> assertNotNull(deliverer),
				() -> assertEquals("Dominique", deliverer.getName())
				);
	}
	
	@Test
	@DisplayName("Test deleting deliverer informations by id")
	void testDeleteDelivererById() {
		when(delivererRepository.findById(1L))
		.thenReturn(Optional.ofNullable(deliverers.get(0)));
		
		verify(delivererRepository).deleteById(1L);
	}
	
	@Test
	@DisplayName("Test find deliverer with an unknown name")
	void testUnknowName() {
		String name = "xyz";
		
		when(this.delivererRepository.searchDelivererByName("%" + name + "%", request))
		.thenReturn(new PageImpl<>(new ArrayList<>()));
		
		Page<DelivererResponseDto> response = this.delivererService.searchDelivererByName(name, request);
		
		assertAll(
				() -> assertNotNull(response),
				() -> assertEquals(0, response.getContent().size())
				);
	}
	
	@Test
	@DisplayName("Test find deliverer by id failed")
	void testWithAnUnkownId() {
		Long id = 300L;
		 when(this.delivererRepository.findById(id))
		 .thenReturn(Optional.empty());
		 
		 assertThrows(ResourceNotFoundException.class,
				 () -> this.delivererService.findById(id)
				 );
	}
	
	List<Deliverer> generateDeliverer() {
		return List.of(
				Deliverer.builder().name("Emilson").contact("0123456789").build(),
				Deliverer.builder().name("Victor").contact("0987654321").build(),
				Deliverer.builder().name("Fidèle").contact("0564321987").build()
				);
	}
	

}
