package com.Lisiniarivo.Application.Configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.Lisiniarivo.Application.Entity.Deliverer;
import com.Lisiniarivo.Application.Repository.DelivererRepository;

import lombok.AllArgsConstructor;
import net.datafaker.Faker;

@Component
@AllArgsConstructor
@Profile("dev")
public class DataInitializer implements CommandLineRunner{
	
	private final DelivererRepository delivererRepository;
	private final Faker faker = new Faker();
	
	@Override
	public void run(String... args) throws Exception {
		this.generateDeliverer();
	}
	
	private void generateDeliverer() {
		List<Deliverer> deliverers = new ArrayList<>();
		for(int i=0; i<15;i++) {
			deliverers.add(Deliverer.builder()
					.name(faker.name().lastName())
					.contact(faker.phoneNumber().phoneNumber())
					.build());
		}
		this.delivererRepository.saveAll(deliverers);
//		this.delivererRepository.saveAll(
//				List.of(
//			Deliverer.builder()
//			.name("Emilson")
//			.contact("+1234567890")
//			.build(),
//			Deliverer.builder()
//			.name("Victor")
//			.contact("+7891234560")
//			.build()
//						)
//				);
		
	}

}
