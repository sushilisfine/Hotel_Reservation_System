package com.myhotel.managment.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.myhotel.managment.controller.HotelController;
import com.myhotel.managment.domain.Hotel;
import com.myhotel.managment.dto.HotelDTO;
import com.myhotel.managment.service.HotelService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class HotelControllerImpl implements HotelController {

	@Autowired
	private HotelService hotelService;

	@Override
	public ResponseEntity<HotelDTO> add(HotelDTO hotel) {
		try {
			return new ResponseEntity<>(hotelService.create(hotel), HttpStatus.CREATED);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<HotelDTO> update(Long hotelId, HotelDTO hotelDTO) {

		hotelDTO.setId(hotelId);

		try {
			if (validateHotel(hotelId))
				return new ResponseEntity<>(hotelService.update(hotelDTO), HttpStatus.OK);
			else
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (RuntimeException e) {
			log.info("Updating hotel failed");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<List<HotelDTO>> getAll() {
		try {
			return new ResponseEntity<>(hotelService.getAll(), HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private boolean validateHotel(Long hotelId) {

		Hotel hotel = hotelService.get(hotelId);

		if (hotel.getId() == null) {
			log.info("Unable to find hotel with id : {}", hotelId);
			return false;
		}
		return true;
	}

}
