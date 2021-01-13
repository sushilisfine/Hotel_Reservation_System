package com.myhotel.managment.domain;

import javax.persistence.Column;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Room", indexes = { @Index(name = "roomIdx", columnList = "roomCode", unique = true) })
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Integer roomCode;

	private Double charges;

	private String roomCategory;

	@Column(columnDefinition = "boolean default true")
	private Boolean isAvailable;

	@ManyToOne
	@JoinColumn(name = "hotel_id", referencedColumnName = "id")
	private Hotel hotel;

}
