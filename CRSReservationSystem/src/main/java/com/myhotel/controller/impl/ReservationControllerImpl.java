package com.myhotel.controller.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.myhotel.controller.ReservationController;
import com.myhotel.dto.request.ReservationRequestDTO;
import com.myhotel.dto.response.ReservationResponseDTO;
import com.myhotel.service.ReservationService;

@RestController
public class ReservationControllerImpl implements ReservationController {

	@Autowired
	private ReservationService reservationService;

	Logger logger = LoggerFactory.getLogger(ReservationControllerImpl.class);

	@Override
	public ResponseEntity<ReservationResponseDTO> add(ReservationRequestDTO reservation) {

		return new ResponseEntity<>(reservationService.createReservation(reservation), HttpStatus.CREATED);
	}

}
