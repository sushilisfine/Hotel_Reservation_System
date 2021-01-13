package com.myhotel.managment.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Hotel", indexes = { @Index(name = "hotelIdx", columnList = "hotelCode", unique = true) })
public class Hotel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long hotelCode;

	private String address;

	private Long contact;

	@OneToMany(mappedBy = "hotel")
	private List<Offer> offers;

	@OneToMany(mappedBy = "hotel")
	private List<Room> room;

}
