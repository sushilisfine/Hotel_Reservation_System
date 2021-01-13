package com.myhotel.managment.service;

import java.util.List;

import com.myhotel.managment.dto.request.OfferRequestDTO;
import com.myhotel.managment.dto.response.OfferResponseDTO;

public interface OfferService {

	OfferResponseDTO addOffer(Long hotelCode, OfferRequestDTO offer);

	OfferResponseDTO updateOffer(Long hotelCode, Integer offerCode, OfferRequestDTO offer);

	List<OfferResponseDTO> getAllOffers(Long hotelCode);

	OfferResponseDTO deleteOffer(Long hotelCode);

}
