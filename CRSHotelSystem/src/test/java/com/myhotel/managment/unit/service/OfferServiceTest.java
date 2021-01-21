package com.myhotel.managment.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;

import com.myhotel.managment.domain.Category;
import com.myhotel.managment.domain.Hotel;
import com.myhotel.managment.domain.Offer;
import com.myhotel.managment.dto.OfferDTO;
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

	private OfferDTO offerDtoObj() {
		OfferDTO offer = new OfferDTO();
		offer.setValue(1000.00);
		offer.setCategoryId(1L);
		offer.setHotelId(1L);
		offer.setId(1L);
		return offer;
	}

	private Offer offerObj() {
		Offer offer = new Offer();
		offer.setId(1L);
		offer.setCategory(Category.builder().id(1L).build());
		offer.setHotel(Hotel.builder().id(1L).build());
		offer.setValue(1000.00);
		return offer;
	}

	@Test
	void test1AddOffer() {
		OfferDTO offerDTO = offerDtoObj();
		Offer offer = offerObj();

		doReturn(offer).when(offerRepository).save(Mockito.any(Offer.class));

		OfferDTO createdOffer = offerService.add(offerDTO);

		assertEquals(createdOffer.getValue(), offerDTO.getValue());
	}

	@Test
	void test2AddOffer() {
		OfferDTO offerDTO = offerDtoObj();
		Offer offer = offerObj();

		doReturn(offer).when(offerRepository).save(Mockito.any(Offer.class));

		OfferDTO createdOffer = offerService.add(offerDTO);

		assertNotNull(createdOffer);
	}

	@Test
	void test3UpdateOffer() {
		OfferDTO offerDTO = offerDtoObj();

		OfferDTO updatedOffer = offerService.update(offerDTO);

		assertEquals(updatedOffer.getValue(), offerDTO.getValue());
	}

}
