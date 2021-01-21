package com.myhotel.managment.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.myhotel.managment.domain.Category;
import com.myhotel.managment.domain.Hotel;
import com.myhotel.managment.domain.Offer;
import com.myhotel.managment.dto.OfferDTO;
import com.myhotel.managment.repository.CategoryRepository;
import com.myhotel.managment.repository.HotelRepository;
import com.myhotel.managment.repository.OfferRepository;
import com.myhotel.managment.service.OfferService;

@Service
public class OfferServiceImpl implements OfferService {

	private OfferRepository offerRepository;
	private HotelRepository hotelRepository;
	private CategoryRepository categoryRepository;

	public OfferServiceImpl(OfferRepository offerRepository, HotelRepository hotelRepository,
			CategoryRepository categoryRepository) {
		this.offerRepository = offerRepository;
		this.hotelRepository = hotelRepository;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public OfferDTO add(OfferDTO offerDTO) {

		Offer offer = converteDTOToEntity(offerDTO);
		return converteEntityToDTO(offerRepository.save(offer));

	}

	@Override
	public OfferDTO update(OfferDTO offerDTO) {
		Offer offer = converteDTOToEntity(offerDTO);
		offerRepository.save(offer);
		return converteEntityToDTO(offer);
	}

	@Override
	public Long delete(Long offerId) {
		offerRepository.deleteById(offerId);
		return offerId;
	}

	@Override
	public List<OfferDTO> getAll(Long hotelId, Long categoryId) {
		List<Offer> offer = getOffers(hotelId, categoryId);
		return converteEntityToDTO(offer);
	}

	@Override
	public Offer getOffer(Long offerId) {
		Optional<Offer> offer = offerRepository.findById(offerId);
		return offer.isPresent() ? offer.get() : new Offer();
	}

	private Offer converteDTOToEntity(OfferDTO offerDTO) {
		Hotel hotel = Hotel.builder().id(offerDTO.getHotelId()).build();
		Category category = Category.builder().hotel(hotel).id(offerDTO.getCategoryId()).build();
		return Offer.builder().id(offerDTO.getId()).value(offerDTO.getValue()).hotel(hotel).category(category).build();
	}

	private OfferDTO converteEntityToDTO(Offer offer) {
		return OfferDTO.builder().id(offer.getId()).value(offer.getValue()).categoryId(offer.getCategory().getId())
				.hotelId(offer.getHotel().getId()).build();
	}

	private List<OfferDTO> converteEntityToDTO(List<Offer> offers) {
		List<OfferDTO> offerDTO = new ArrayList<>();
		offers.forEach(offer -> offerDTO.add(converteEntityToDTO(offer)));
		return offerDTO;
	}

	public Hotel getHotel(Long hotelId) {
		Optional<Hotel> hotel = hotelRepository.findById(hotelId);
		return hotel.isPresent() ? hotel.get() : new Hotel();

	}

	public List<Offer> getOffers(Long hotelId, Long categoryId) {
		Hotel hotel = Hotel.builder().id(hotelId).build();
		Category category = Category.builder().hotel(hotel).id(categoryId).build();
		Optional<List<Offer>> offer = offerRepository.findByHotelAndCategory(hotel, category);
		return offer.isPresent() ? offer.get() : new ArrayList<>();
	}

	public Category getCategory(Long categoryId) {
		Optional<Category> category = categoryRepository.findById(categoryId);
		return category.isPresent() ? category.get() : new Category();
	}

}
