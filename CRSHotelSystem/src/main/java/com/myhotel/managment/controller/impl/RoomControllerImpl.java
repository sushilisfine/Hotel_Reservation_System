package com.myhotel.managment.controller.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.myhotel.managment.controller.RoomController;
import com.myhotel.managment.domain.Category;
import com.myhotel.managment.domain.Hotel;
import com.myhotel.managment.domain.Room;
import com.myhotel.managment.dto.RoomDTO;
import com.myhotel.managment.service.RoomService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class RoomControllerImpl implements RoomController {

	@Autowired
	private RoomService roomService;

	@Override
	public ResponseEntity<RoomDTO> add(Long hotelId, RoomDTO room) {

		room.setHotelId(hotelId);

		try {
			if (validateHotel(hotelId))
				return new ResponseEntity<>(roomService.add(room), HttpStatus.CREATED);
			else
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (RuntimeException e) {
			log.info("Creating new Room failed");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<RoomDTO> update(Long hotelId, Long roomId, RoomDTO roomDTO) {

		roomDTO.setHotelId(hotelId);
		roomDTO.setId(roomId);

		try {
			if (validateHotelAndCategoryAndRoom(hotelId, roomDTO.getCategoryId(), roomId))
				return new ResponseEntity<>(roomService.update(roomDTO), HttpStatus.OK);
			else
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (RuntimeException e) {
			log.info("Updating Room failed");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<List<RoomDTO>> getAll(Long hotelId) {

		try {
			if (validateHotel(hotelId))
				return new ResponseEntity<>(roomService.getAll(hotelId), HttpStatus.OK);
			else
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (RuntimeException e) {
			log.info("Getting all Room failed");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<List<RoomDTO>> getAvailable(Long hotelId, LocalDate from, LocalDate to, Long categoryId) {

		try {
			if (validateHotelAndCategory(hotelId, categoryId) && validateDates(from, to))
				return new ResponseEntity<>(roomService.getByParams(hotelId, from, to, categoryId), HttpStatus.OK);
			else
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (RuntimeException e) {
			log.info("Getting Rooms failed");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private boolean validateHotel(Long hotelId) {

		Hotel hotel = roomService.getHotel(hotelId);
		if (hotel.getId() == null) {
			log.info("Unable to find hotel with id : {}", hotelId);
			return false;
		}
		return true;
	}

	private boolean validateCategory(Long categoryId) {

		Category category = roomService.getCategory(categoryId);
		if (category.getId() == null) {
			log.info("Unable to find category with id : {}", categoryId);
			return false;
		}
		return true;
	}

	private boolean validateRoom(Long roomId) {
		Room room = roomService.get(roomId);
		if (room.getId() == null) {
			log.info("Unable to find room with id : {}", roomId);
			return false;
		}
		return true;
	}

	private boolean validateDates(LocalDate from, LocalDate to) {
		if (from.isAfter(to)) {
			log.error("From date should be less than to date");
			return false;
		}
		return true;
	}

	private boolean validateHotelAndCategory(Long hotelId, Long categoryId) {
		return validateHotel(hotelId) && validateCategory(categoryId);
	}

	private boolean validateHotelAndCategoryAndRoom(Long hotelId, Long categoryId, Long roomId) {
		return validateHotel(hotelId) && validateCategory(categoryId) && validateRoom(roomId);
	}
}
