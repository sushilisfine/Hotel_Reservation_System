package com.myhotel.managment.unit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.myhotel.managment.controller.HotelController;
import com.myhotel.managment.controller.OfferController;
import com.myhotel.managment.dto.request.OfferRequestDTO;
import com.myhotel.managment.dto.response.OfferResponseDTO;
import com.myhotel.managment.service.HotelService;
import com.myhotel.managment.service.OfferService;

class OffersControllerTest extends AbstractTest {

	private MockMvc mockMvc;

	@Mock
	private OfferService offerService;

	@Mock
	private HotelService hotelService;

	@InjectMocks
	private OfferController offerController;

	@InjectMocks
	private HotelController hotelController;

	@BeforeEach
	public void setup() {

		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(offerController, hotelController).build();
	}

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

		lenient().when(offerService.addOffer(1L, offerRequestDTO)).thenReturn(offerResponeDTO);

		mockMvc.perform(post("/api/v1/hotels/1/offers").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(offerRequestDTO)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());

		assertEquals(offerRequestDTO.getOfferCode(), offerResponeDTO.getOfferCode());
	}

}
