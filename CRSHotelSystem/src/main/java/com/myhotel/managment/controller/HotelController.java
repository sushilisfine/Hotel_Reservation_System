package com.myhotel.managment.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myhotel.managment.dto.HotelDTO;

@RequestMapping("/api/v1/hotels")
public interface HotelController {

	@PostMapping
	public ResponseEntity<HotelDTO> add(@RequestBody HotelDTO hotel);

	@PutMapping("/{hotel_id}")
	public ResponseEntity<HotelDTO> update(@PathVariable("hotel_id") Long hotelId, @RequestBody HotelDTO hotelDTO);

	@GetMapping
	public ResponseEntity<List<HotelDTO>> getAll();
}
