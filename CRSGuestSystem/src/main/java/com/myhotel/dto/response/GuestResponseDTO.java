package com.myhotel.dto.response;

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
public class GuestResponseDTO {

	private Long id;

	private Long guestCode;

	private String name;

	private String email;

	private Long contact;

}
