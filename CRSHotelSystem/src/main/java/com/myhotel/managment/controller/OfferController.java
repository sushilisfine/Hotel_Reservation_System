package com.myhotel.managment.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myhotel.managment.dto.OfferDTO;

@RequestMapping("/api/v1/hotels/")
public interface OfferController {

	@PostMapping("{hotel_id}/offers")
	public ResponseEntity<OfferDTO> add(@PathVariable("hotel_id") Long hotelId, @RequestBody OfferDTO offer);

	@PutMapping("{hotel_id}/offers/{offer_id}")
	public ResponseEntity<OfferDTO> update(@PathVariable("hotel_id") Long hotelId,
			@PathVariable("offer_id") Long offerId, @RequestBody OfferDTO offer);

	@GetMapping("{hotel_id}/offers")
	public ResponseEntity<List<OfferDTO>> getAll(@PathVariable("hotel_id") Long hotelId,
			@RequestParam("category_id") Long categoryId);

	@DeleteMapping("{hotel_id}/offers/{offer_id}")
	public ResponseEntity<Long> delete(@PathVariable("hotel_id") Long hotelId, @PathVariable("offer_id") Long offerId,
			@RequestParam("category_id") Long categoryId);
}
