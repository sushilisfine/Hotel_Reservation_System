package com.myhotel.managment.dto.response;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.ElementCollection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponseDTO {

	private Integer id;

	private Integer roomCode;

	private Double charges;

	private String roomCategory;

	@ElementCollection
	private List<LocalDate> bookedDates;

}
