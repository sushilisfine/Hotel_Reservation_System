package com.myhotel.controller;

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

import com.myhotel.dto.request.GuestRequestDTO;
import com.myhotel.dto.response.GuestResponseDTO;
import com.myhotel.service.GuestService;

@RestController
@RequestMapping("/api/v1")
public class GuestController {

	@Autowired
	GuestService guestService;

	@PostMapping(value = "/guests")
	public ResponseEntity<GuestResponseDTO> add(@RequestBody GuestRequestDTO guest) {

		return new ResponseEntity<>(guestService.createGuest(guest), HttpStatus.CREATED);
	}

	@PutMapping(value = "/guests/{guest_code}")
	public ResponseEntity<GuestResponseDTO> update(@PathVariable("guest_code") Long guestCode,
			@RequestBody GuestRequestDTO guestRequestDTO) {

		return new ResponseEntity<>(guestService.updateGuest(guestCode, guestRequestDTO), HttpStatus.OK);
	}

	@GetMapping(value = "/guests")
	public ResponseEntity<List<GuestResponseDTO>> getAll() {

		return new ResponseEntity<>(guestService.getAllGuests(), HttpStatus.OK);
	}

	@PutMapping(value = "/guests/{guest_code}")
	public ResponseEntity<GuestResponseDTO> get(@PathVariable("guest_code") Long guestCode) {

		return new ResponseEntity<>(guestService.getGuest(guestCode), HttpStatus.OK);
	}

}
