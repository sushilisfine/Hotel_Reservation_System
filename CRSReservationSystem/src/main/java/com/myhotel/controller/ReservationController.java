package com.myhotel.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myhotel.dto.request.ReservationRequestDTO;
import com.myhotel.dto.response.ReservationResponseDTO;

@RequestMapping("/api/v1/reservations")
public interface ReservationController {

	@PostMapping
	public ResponseEntity<ReservationResponseDTO> add(@RequestBody ReservationRequestDTO reservation);
}
