package com.myhotel.managment.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.myhotel.managment.dto.request.RoomRequestDTO;
import com.myhotel.managment.dto.response.RoomResponseDTO;
import com.myhotel.managment.service.RoomService;

@RestController
@RequestMapping("/api/v1")
public class RoomController {

	@Autowired
	private RoomService roomService;

	Logger logger = LoggerFactory.getLogger(RoomController.class);

	@PostMapping(value = "/hotels/{hotel_code}/rooms")
	public ResponseEntity<RoomResponseDTO> add(@PathVariable("hotel_code") Long hotelCode,
			@RequestBody RoomRequestDTO hotel) {

		return new ResponseEntity<>(roomService.addRoom(hotelCode, hotel), HttpStatus.CREATED);
	}

	@PutMapping(value = "/hotels/{hotel_code}/rooms/{room_code}")
	public ResponseEntity<RoomResponseDTO> update(@PathVariable("hotel_code") Long hotelCode,
			@PathVariable("room_code") Integer roomCode, @RequestBody RoomRequestDTO roomRequestDTO) {

		return new ResponseEntity<>(roomService.updateRoom(hotelCode, roomCode, roomRequestDTO), HttpStatus.OK);
	}

	@GetMapping(value = "/hotels/{hotel_code}/rooms")
	public ResponseEntity<List<RoomResponseDTO>> getAll(@PathVariable("hotel_code") Long hotelCode) {

		return new ResponseEntity<>(roomService.getAllRooms(hotelCode), HttpStatus.OK);
	}

	/*
	 * @GetMapping(value = "/hotels/{hotel_code}/rooms/availability") public
	 * ResponseEntity<List<HotelResponseDTO>> getAvailableRooms(
	 * 
	 * @PathVariable(required = true, name = "hotel_code") String hotelCode,
	 * 
	 * @RequestParam(required = false) Map<String, String> qparams) {
	 * 
	 * return new ResponseEntity<>(roomService.getAvailableRooms(), HttpStatus.OK);
	 * }
	 */

}
