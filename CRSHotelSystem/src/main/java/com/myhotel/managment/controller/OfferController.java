package com.myhotel.managment.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myhotel.managment.dto.request.OfferRequestDTO;
import com.myhotel.managment.dto.response.OfferResponseDTO;
import com.myhotel.managment.service.OfferService;

@RestController
@RequestMapping("/api/v1")
public class OfferController {

	@Autowired
	private OfferService offerService;

	Logger logger = LoggerFactory.getLogger(OfferController.class);

	@PostMapping(value = "/hotels/{hotel_code}/offers")
	public ResponseEntity<OfferResponseDTO> add(@PathVariable("hotel_code") Long hotelCode,
			@RequestBody OfferRequestDTO offer) {

		return new ResponseEntity<>(offerService.addOffer(hotelCode, offer), HttpStatus.CREATED);
	}

	@PutMapping(value = "/hotels/{hotel_code}/offers/{{offer_code}}")
	public ResponseEntity<OfferResponseDTO> update(@PathVariable("hotel_code") Long hotelCode,
			@PathVariable("offercode") Integer offerCode, @RequestBody OfferRequestDTO offer) {

		return new ResponseEntity<>(offerService.updateOffer(hotelCode, offerCode, offer), HttpStatus.OK);
	}

	@GetMapping(value = "/hotels/{hotel_code}/offers")
	public ResponseEntity<List<OfferResponseDTO>> getAll(@PathVariable("hotel_code") Long hotelCode) {

		return new ResponseEntity<>(offerService.getAllOffers(hotelCode), HttpStatus.OK);
	}

	@DeleteMapping(value = "/hotels/{hotel_code}/offers/{{offer_code}}")
	public ResponseEntity<OfferResponseDTO> delete(@PathVariable("hotel_code") Long hotelCode,
			@PathVariable("offercode") Integer offerCode) {

		return new ResponseEntity<>(offerService.deleteOffer(hotelCode), HttpStatus.OK);
	}
}
