package com.Lisiniarivo.Application.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Commande") // change the entity name during the query run
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalDate deliveryDate;
	private String reference;
	private double deliveryFee;
	private double total;
	private boolean delivered;
	
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	
	@Embedded
	private Client client;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Cart> cart;

	@ManyToOne
	@JoinColumn(name = "deliverer_id")
	private Deliverer deliverer;
	
	@Lob
	@Column(columnDefinition="TEXT")
	private String description;
	
	@PrePersist
	private void generateReference() {
		if(this.client != null && this.deliveryDate != null) {
			String date = this.deliveryDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
			String initials = this.generateInitials(client.getName());
			this.reference = "CMD-" + date + "-" + initials + "/" + this.deliverer.getId();
		}
	}
	
	private String generateInitials(String name) {
		if(name != null && name.isEmpty()) return "XX";	
		String[] parts = name.trim().split("\\s+");
		StringBuilder sb = new StringBuilder();
		for(String part : parts) {
			sb.append(Character.toUpperCase(part.charAt(0)));
		}
		return sb.toString();
	}
}
