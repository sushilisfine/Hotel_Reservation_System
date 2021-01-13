package com.myhotel.managment.dto.response;

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
public class HotelResponseDTO {

	private Long id;

	private Long hotelCode;

	private String address;

	private Long contact;

}
