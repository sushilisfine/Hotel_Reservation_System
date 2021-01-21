package com.myhotel.managment.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.myhotel.managment.domain.Hotel;
import com.myhotel.managment.dto.HotelDTO;
import com.myhotel.managment.repository.HotelRepository;
import com.myhotel.managment.service.HotelService;

@Service
public class HotelServiceImpl implements HotelService {

	private HotelRepository hotelRepository;

	public HotelServiceImpl(HotelRepository hotelRepository) {
		this.hotelRepository = hotelRepository;
	}

	@Override
	public HotelDTO create(HotelDTO hotelDTO) {
		Hotel hotel = converteDTOToEntity(hotelDTO);
		return converteEntityToDTO(hotelRepository.save(hotel));
	}

	@Override
	public HotelDTO update(HotelDTO hotelDTO) {

		Hotel hotel = converteDTOToEntity(hotelDTO);
		hotelRepository.save(hotel);
		return converteEntityToDTO(hotel);
	}

	@Override
	public List<HotelDTO> getAll() {

		List<Hotel> hotels = hotelRepository.findAll();
		return converteEntityToDTO(hotels);
	}

	private Hotel converteDTOToEntity(HotelDTO hotelDTO) {

		return Hotel.builder().id(hotelDTO.getId()).address(hotelDTO.getAddress()).contact(hotelDTO.getContact())
				.build();

	}

	private HotelDTO converteEntityToDTO(Hotel hotel) {

		return HotelDTO.builder().address(hotel.getAddress()).contact(hotel.getContact()).id(hotel.getId()).build();

	}

	private List<HotelDTO> converteEntityToDTO(List<Hotel> hotels) {

		List<HotelDTO> hotelDTO = new ArrayList<>();
		hotels.forEach(hotel -> hotelDTO.add(converteEntityToDTO(hotel)));
		return hotelDTO;
	}

	@Override
	public Hotel get(Long hotelId) {
		Optional<Hotel> hotel = hotelRepository.findById(hotelId);
		return hotel.orElse(new Hotel());
	}

}
