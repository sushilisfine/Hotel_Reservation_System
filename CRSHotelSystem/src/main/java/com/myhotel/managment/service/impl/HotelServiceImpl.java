package com.myhotel.managment.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.myhotel.managment.domain.Hotel;
import com.myhotel.managment.dto.request.HotelRequestDTO;
import com.myhotel.managment.dto.response.HotelResponseDTO;
import com.myhotel.managment.repository.HotelRepository;
import com.myhotel.managment.service.HotelService;

@Service
public class HotelServiceImpl implements HotelService {

	private HotelRepository hotelRepository;

	Logger logger = LoggerFactory.getLogger(HotelServiceImpl.class);

	public HotelServiceImpl(HotelRepository hotelRepository) {
		this.hotelRepository = hotelRepository;
	}

	@Override
	public HotelResponseDTO createHotel(HotelRequestDTO hotelDTO) {

		Hotel hotelDb = getHotelFromHotelCode(hotelDTO.getHotelCode());

		if (hotelDb != null) {
			logger.error("Hotel with hotel code : {} , already exists.", hotelDTO.getHotelCode());
			return null;
		}
		Hotel hotel = converteDTOToEntity(hotelDTO);

		return converteEntityToDTO(hotelRepository.save(hotel));

	}

	@Override
	public HotelResponseDTO updateHotel(Long hotelCode, HotelRequestDTO hotelRequestDTO) {
		Hotel hotelDb = getHotelFromHotelCode(hotelCode);

		updateHotelFromDTO(hotelDb, hotelRequestDTO);
		hotelDb = hotelRepository.save(hotelDb);

		return converteEntityToDTO(hotelDb);
	}

	private Hotel updateHotelFromDTO(Hotel hotel, HotelRequestDTO hotelRequestDTO) {

		hotel.setAddress(hotelRequestDTO.getAddress());
		hotel.setContact(hotelRequestDTO.getContact());
		hotel.setHotelCode(hotelRequestDTO.getHotelCode());

		return hotel;

	}

	@Override
	public List<HotelResponseDTO> getAllHotels() {

		List<Hotel> hotels = hotelRepository.findAll();
		List<HotelResponseDTO> hotelResponseDTO = new ArrayList<>();

		hotels.forEach(hotel -> {
			hotelResponseDTO.add(converteEntityToDTO(hotel));

		});
		return hotelResponseDTO;
	}

	private Hotel converteDTOToEntity(HotelRequestDTO hotelDTO) {

		return Hotel.builder().address(hotelDTO.getAddress()).contact(hotelDTO.getContact())
				.hotelCode(hotelDTO.getHotelCode()).build();

	}

	private HotelResponseDTO converteEntityToDTO(Hotel hotel) {

		return HotelResponseDTO.builder().address(hotel.getAddress()).contact(hotel.getContact())
				.hotelCode(hotel.getHotelCode()).id(hotel.getId()).build();

	}

	private Hotel getHotelFromHotelCode(Long hotelCode) {
		return hotelRepository.findByHotelCode(hotelCode);
	}

}
