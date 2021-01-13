package com.myhotel.managment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HotelRequestDTO {

	private Long hotelCode;

	private String address;

	private Long contact;

}
