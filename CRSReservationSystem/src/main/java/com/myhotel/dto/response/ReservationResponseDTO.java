package com.myhotel.dto.response;

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

public class ReservationResponseDTO {

	private Long id;

	private Long guestId;

	private Long hotelId;

	private Long roomId;

	private Long categoryId;

	private Long offerId;

	private LocalDate startDate;

	private LocalDate endDate;

	private Double charges;
}
