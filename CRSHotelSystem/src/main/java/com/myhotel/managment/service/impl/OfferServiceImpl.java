package com.myhotel.managment.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.myhotel.managment.domain.Hotel;
import com.myhotel.managment.domain.Offer;
import com.myhotel.managment.dto.request.OfferRequestDTO;
import com.myhotel.managment.dto.response.OfferResponseDTO;
import com.myhotel.managment.repository.HotelRepository;
import com.myhotel.managment.repository.OfferRepository;
import com.myhotel.managment.service.OfferService;

@Service
public class OfferServiceImpl implements OfferService {

	Logger logger = LoggerFactory.getLogger(OfferServiceImpl.class);

	private OfferRepository offerRepository;
	private HotelRepository hotelRepository;

	public OfferServiceImpl(OfferRepository offerRepository, HotelRepository hotelRepository) {
		this.offerRepository = offerRepository;
		this.hotelRepository = hotelRepository;
	}

	@Override
	public OfferResponseDTO addOffer(Long hotelCode, OfferRequestDTO offerDTO) {

		Offer offerDb = offerRepository.findByOfferCode(offerDTO.getOfferCode());

		if (offerDb != null) {

			logger.error("Offer with Offer Code : {} , already exists.", offerDTO.getOfferCode());
			return null;

		}

		Hotel hotel = getHotelFromHotelCode(hotelCode);

		Offer offer = converteDTOToEntity(offerDTO);
		offer.setHotel(hotel);
		return converteEntityToDTO(offerRepository.save(offer));

	}

	@Override
	public OfferResponseDTO updateOffer(Long hotelCode, Integer offerCode, OfferRequestDTO offerRequestDTO) {

		Hotel hotelDb = hotelRepository.findByHotelCode(hotelCode);

		Offer offer = offerRepository.findByOfferCodeAndHotel(offerCode, hotelDb);

		return updateOfferFromDTO(offer, offerRequestDTO);
	}

	private OfferResponseDTO updateOfferFromDTO(Offer offer, OfferRequestDTO offerRequestDTO) {

		offer.setOfferCode(offerRequestDTO.getOfferCode());
		offer.setValue(offerRequestDTO.getValue());
		return converteEntityToDTO(offer);

	}

	@Override
	public List<OfferResponseDTO> getAllOffers(Long hotelCode) {
		Hotel hotel = getHotelFromHotelCode(hotelCode);
		List<Offer> offers = offerRepository.findAllByHotel(hotel);
		List<OfferResponseDTO> offerResponseDTO = new ArrayList<>();

		offers.forEach(offer -> {
			offerResponseDTO.add(converteEntityToDTO(offer));

		});
		return offerResponseDTO;
	}

	@Override
	public OfferResponseDTO deleteOffer(Long hotelCode) {

		Hotel hotel = getHotelFromHotelCode(hotelCode);
		OfferResponseDTO offerResponseDTO = offerRepository.findByHotel(hotel);
		offerRepository.findDeleteByHotel(hotel);
		return offerResponseDTO;
	}

	private Offer converteDTOToEntity(OfferRequestDTO offerDTO) {

		return Offer.builder().offerCode(offerDTO.getOfferCode()).value(offerDTO.getValue()).build();

	}

	private OfferResponseDTO converteEntityToDTO(Offer offer) {

		return OfferResponseDTO.builder().offerCode(offer.getOfferCode()).value(offer.getValue()).build();

	}

	private Hotel getHotelFromHotelCode(Long hotelCode) {
		return hotelRepository.findByHotelCode(hotelCode);
	}

}
