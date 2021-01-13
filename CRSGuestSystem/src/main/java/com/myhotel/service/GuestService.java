package com.myhotel.service;

import java.util.List;

import com.myhotel.dto.request.GuestRequestDTO;
import com.myhotel.dto.response.GuestResponseDTO;

public interface GuestService {

	GuestResponseDTO createGuest(GuestRequestDTO guestDTO);

	List<GuestResponseDTO> getAllGuests();

	GuestResponseDTO updateGuest(Long guestCode, GuestRequestDTO guestRequestDTO);

	GuestResponseDTO getGuest(Long guestCode);

}
