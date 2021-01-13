package com.myhotel.managment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequestDTO {

	private Integer roomCode;

	private Double charges;

	private String roomCategory;

	private Boolean isAvailable;

}
