package com.myhotel.managment.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myhotel.managment.dto.RoomDTO;

@RequestMapping("/api/v1/hotels/")
public interface RoomController {

	@PostMapping("{hotel_id}/rooms")
	public ResponseEntity<RoomDTO> add(@PathVariable("hotel_id") Long hotelId, @RequestBody RoomDTO room);

	@PutMapping("{hotel_id}/rooms/{room_id}")
	public ResponseEntity<RoomDTO> update(@PathVariable("hotel_id") Long hotelId, @PathVariable("room_id") Long roomId,
			@RequestBody RoomDTO roomDTO);

	@GetMapping("{hotel_id}/rooms")
	public ResponseEntity<List<RoomDTO>> getAll(@PathVariable("hotel_id") Long hotelId);

	@GetMapping("{hotel_id}/rooms/availability")
	public ResponseEntity<List<RoomDTO>> getAvailable(@PathVariable(required = true, name = "hotel_id") Long hotelId,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to, @RequestParam Long categoryId);

}
