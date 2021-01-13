package com.myhotel.managment.service;

import java.util.List;

import com.myhotel.managment.dto.request.HotelRequestDTO;
import com.myhotel.managment.dto.response.HotelResponseDTO;

public interface HotelService {

	HotelResponseDTO createHotel(HotelRequestDTO hotelDTO);

	List<HotelResponseDTO> getAllHotels();

	HotelResponseDTO updateHotel(Long hotelCode, HotelRequestDTO hotelRequestDTO);

}
