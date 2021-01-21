package com.myhotel.managment.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Hotel")
public class Hotel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String address;

	private Long contact;

	@OneToMany(mappedBy = "hotel")
	private List<Offer> offers;

	@OneToMany(mappedBy = "hotel")
	private List<Room> room;

	@OneToMany(mappedBy = "hotel")
	private List<Category> category;

}
