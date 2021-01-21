package com.myhotel.dto.request;

import java.time.LocalDate;

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
public class ReservationRequestDTO {

	private Long hotelId;

	private Long categoryId;

	private LocalDate startDate;

	private LocalDate endDate;

}
