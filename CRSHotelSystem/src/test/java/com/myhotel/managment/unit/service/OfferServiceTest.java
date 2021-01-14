package com.myhotel.managment.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;

import com.myhotel.managment.domain.Hotel;
import com.myhotel.managment.domain.Offer;
import com.myhotel.managment.dto.request.OfferRequestDTO;
import com.myhotel.managment.dto.response.OfferResponseDTO;
import com.myhotel.managment.repository.HotelRepository;
import com.myhotel.managment.repository.OfferRepository;
import com.myhotel.managment.service.impl.OfferServiceImpl;

@ExtendWith(MockitoExtension.class)
@PropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
class OfferServiceTest {

	@InjectMocks
	private OfferServiceImpl offerService;

	@Mock
	private OfferRepository offerRepository;

	@Mock
	private HotelRepository hotelRepository;

	private OfferRequestDTO offerReqObj() {
		OfferRequestDTO offer = new OfferRequestDTO();
		offer.setOfferCode(1);
		offer.setValue(1000.00);
		return offer;
	}

	private Offer offerObj() {
		Offer offer = new Offer();
		offer.setId(1);
		offer.setOfferCode(1);
		offer.setValue(1000.00);
		return offer;
	}

	private Hotel hotelObj() {
		Hotel hotel = new Hotel();
		hotel.setAddress("Nagpur");
		hotel.setContact(9999999999L);
		hotel.setHotelCode(1L);
		return hotel;
	}

	@Test
	void test1AddOffer() {
		OfferRequestDTO offerRequestDTO = offerReqObj();
		Offer offer = offerObj();

		doReturn(null).when(offerRepository).findByOfferCode(offerRequestDTO.getOfferCode());
		doReturn(offer).when(offerRepository).save(Mockito.any(Offer.class));

		OfferResponseDTO createdOffer = offerService.addOffer(1L, offerRequestDTO);

		assertEquals(createdOffer.getValue(), offerRequestDTO.getValue());
		assertEquals(createdOffer.getOfferCode(), offerRequestDTO.getOfferCode());
	}

	@Test
	void test2AddOffer() {
		OfferRequestDTO offerRequestDTO = offerReqObj();
		Offer offer = offerObj();

		doReturn(offer).when(offerRepository).findByOfferCode(offerRequestDTO.getOfferCode());
		lenient().doReturn(offer).when(offerRepository).save(Mockito.any(Offer.class));

		OfferResponseDTO createdOffer = offerService.addOffer(1L, offerRequestDTO);

		assertNull(createdOffer);
	}

	@Test
	void test3UpdateOffer() {
		OfferRequestDTO offerRequestDTO = offerReqObj();
		Offer offer = offerObj();
		Hotel hotel = hotelObj();
		doReturn(hotel).when(hotelRepository).findByHotelCode(1L);
		lenient().doReturn(offer).when(offerRepository).findByOfferCodeAndHotel(1, hotel);

		OfferResponseDTO updatedOffer = offerService.updateOffer(1L, 1, offerRequestDTO);

		assertEquals(updatedOffer.getValue(), offerRequestDTO.getValue());
		assertEquals(updatedOffer.getOfferCode(), offerRequestDTO.getOfferCode());
	}

}
