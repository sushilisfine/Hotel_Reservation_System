package com.myhotel.managment.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.myhotel.managment.controller.OfferController;
import com.myhotel.managment.domain.Category;
import com.myhotel.managment.domain.Hotel;
import com.myhotel.managment.domain.Offer;
import com.myhotel.managment.dto.OfferDTO;
import com.myhotel.managment.service.OfferService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class OfferControllerImpl implements OfferController {

	@Autowired
	private OfferService offerService;

	@Override
	public ResponseEntity<OfferDTO> add(Long hotelId, OfferDTO offer) {

		offer.setHotelId(hotelId);

		try {
			if (validateHotelAndCategory(hotelId, offer.getCategoryId()))
				return new ResponseEntity<>(offerService.add(offer), HttpStatus.CREATED);
			else
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (RuntimeException e) {
			log.info("Creating new Offer failed");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<OfferDTO> update(Long hotelId, Long offerId, OfferDTO offer) {

		offer.setHotelId(hotelId);
		offer.setId(offerId);

		try {
			if (validateHotelAndCategoryAndOffer(hotelId, offer.getCategoryId(), offerId))
				return new ResponseEntity<>(offerService.update(offer), HttpStatus.OK);
			else
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (RuntimeException e) {
			log.info("Updating Offer failed");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<List<OfferDTO>> getAll(Long hotelId, Long categoryId) {

		try {
			if (validateHotelAndCategory(hotelId, categoryId))
				return new ResponseEntity<>(offerService.getAll(hotelId, categoryId), HttpStatus.OK);
			else
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (RuntimeException e) {
			log.info("Getting all Offer failed");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Long> delete(Long hotelId, Long categoryId, Long offerId) {

		try {
			if (validateHotelAndCategoryAndOffer(hotelId, categoryId, offerId))
				return new ResponseEntity<>(offerService.delete(offerId), HttpStatus.OK);
			else
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (RuntimeException e) {
			log.info("Deleting Offer failed");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private boolean validateHotel(Long hotelId) {

		Hotel hotel = offerService.getHotel(hotelId);

		if (hotel.getId() == null) {
			log.info("Unable to find guest with id : {}", hotelId);
			return false;
		}
		return true;
	}

	private boolean validateCategory(Long categoryId) {

		Category category = offerService.getCategory(categoryId);
		if (category.getId() == null) {
			log.info("Unable to find category with id : {} ", categoryId);
			return false;
		}
		return true;
	}

	private boolean validateOffer(Long offerId) {
		Offer offer = offerService.getOffer(offerId);
		if (offer.getId() == null) {
			log.info("Unable to find offer with id : {} ", offerId);
			return false;
		}
		return true;
	}

	private boolean validateHotelAndCategory(Long hotelId, Long categoryId) {
		return validateHotel(hotelId) && validateCategory(categoryId);
	}

	private boolean validateHotelAndCategoryAndOffer(Long hotelId, Long categoryId, Long offerId) {
		return validateHotel(hotelId) && validateCategory(categoryId) && validateOffer(offerId);
	}

}
