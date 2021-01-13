package com.myhotel.managment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myhotel.managment.dto.request.HotelRequestDTO;
import com.myhotel.managment.dto.response.HotelResponseDTO;
import com.myhotel.managment.service.HotelService;

@RestController
@RequestMapping("/api/v1")
public class HotelController {

	@Autowired
	HotelService hotelService;

	@PostMapping(value = "/hotels")
	public ResponseEntity<HotelResponseDTO> add(@RequestBody HotelRequestDTO hotel) {

		return new ResponseEntity<>(hotelService.createHotel(hotel), HttpStatus.CREATED);
	}

	@PutMapping(value = "/hotels/{hotel_code}")
	public ResponseEntity<HotelResponseDTO> update(@PathVariable("hotel_code") Long hotelCode,
			@RequestBody HotelRequestDTO hotelRequestDTO) {

		return new ResponseEntity<>(hotelService.updateHotel(hotelCode, hotelRequestDTO), HttpStatus.OK);
	}

	@GetMapping(value = "/hotels")
	public ResponseEntity<List<HotelResponseDTO>> getAll() {

		return new ResponseEntity<>(hotelService.getAllHotels(), HttpStatus.OK);
	}

}
