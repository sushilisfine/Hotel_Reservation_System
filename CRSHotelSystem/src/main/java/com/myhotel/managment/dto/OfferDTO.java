package com.myhotel.managment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OfferDTO {

	private Long id;

	private Long hotelId;

	private Long categoryId;

	private Double value;

}
