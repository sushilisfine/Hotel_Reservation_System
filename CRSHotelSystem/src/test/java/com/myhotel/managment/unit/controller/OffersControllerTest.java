package com.myhotel.managment.unit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import com.myhotel.managment.dto.request.OfferRequestDTO;
import com.myhotel.managment.dto.response.OfferResponseDTO;
import com.myhotel.managment.service.OfferService;

class OffersControllerTest extends AbstractTest {

	@MockBean
	OfferService offerService;

	private OfferRequestDTO getOfferReqDTO() {
		OfferRequestDTO offerRequestDTO = new OfferRequestDTO();
		offerRequestDTO.setOfferCode(null);
		offerRequestDTO.setValue(1000.00);
		return offerRequestDTO;
	}

	private OfferResponseDTO getOfferResDTO() {
		OfferResponseDTO offerResponseDTO = new OfferResponseDTO();
		offerResponseDTO.setId(1);
		offerResponseDTO.setOfferCode(null);
		offerResponseDTO.setValue(1000.00);
		return offerResponseDTO;
	}

	@Test
	void test1CreateOffer() throws Exception {
		OfferRequestDTO offerRequestDTO = getOfferReqDTO();
		OfferResponseDTO offerResponeDTO = getOfferResDTO();

		mvc.perform(post("api/v1/hotels/1/offers").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(offerRequestDTO)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
		assertEquals(offerRequestDTO.getOfferCode(), offerResponeDTO.getOfferCode());
	}

}
