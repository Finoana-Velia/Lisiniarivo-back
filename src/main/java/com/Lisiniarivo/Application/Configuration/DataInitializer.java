package com.Lisiniarivo.Application.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.Lisiniarivo.Application.Entity.Article;
import com.Lisiniarivo.Application.Entity.Category;
import com.Lisiniarivo.Application.Entity.Deliverer;
import com.Lisiniarivo.Application.Entity.Size;
import com.Lisiniarivo.Application.Repository.ArticleRepository;
import com.Lisiniarivo.Application.Repository.DelivererRepository;

import lombok.AllArgsConstructor;
import net.datafaker.Faker;

@Component
@AllArgsConstructor
@Profile("dev")
public class DataInitializer implements CommandLineRunner{
	
	private final DelivererRepository delivererRepository;
	private final ArticleRepository articleRepository;
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
		List<Article> articles = new ArrayList<>();
		for(int i = 0; i < 11 ; i++) {
			articles.add(Article.builder()
					.name(faker.familyGuy().character())
					.image(faker.file().fileName())
					.availability(true)
					.price(faker.money().hashCode())
					.category(Category.ENSEMBLE)
					.sizes(Set.of(Size.L,Size.M,Size.S,Size.XL,Size.XS,
							Size.XXL,Size.XXXL))
					.description(faker.text().text())
					.build());
		}
		this.articleRepository.saveAll(articles);
	}

}
