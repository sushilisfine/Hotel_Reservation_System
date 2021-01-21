package com.myhotel.managment.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomDTO {

	private Long id;

	private Long categoryId;

	private List<LocalDate> bookedDates;

	private Long hotelId;
}
