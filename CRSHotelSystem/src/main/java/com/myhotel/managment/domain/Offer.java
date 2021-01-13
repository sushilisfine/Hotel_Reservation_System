package com.myhotel.managment.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Offer", indexes = { @Index(name = "offerIdx", columnList = "offerCode", unique = true) })
public class Offer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Integer offerCode;

	@ManyToOne
	@JoinColumn(name = "hotel_id", referencedColumnName = "id")
	private Hotel hotel;

	private Double value;

}
