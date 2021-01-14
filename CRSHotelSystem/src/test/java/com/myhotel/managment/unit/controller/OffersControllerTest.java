package com.myhotel.managment.unit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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

	private OfferRequestDTO getOfferReqObj() {
		OfferRequestDTO offerRequestDTO = new OfferRequestDTO();
		offerRequestDTO.setOfferCode(null);
		offerRequestDTO.setValue(1000.00);
		return offerRequestDTO;
	}

	private OfferResponseDTO getOfferResObj() {
		OfferResponseDTO offerResponseDTO = new OfferResponseDTO();
		offerResponseDTO.setId(1);
		offerResponseDTO.setOfferCode(null);
		offerResponseDTO.setValue(1000.00);
		return offerResponseDTO;
	}

	@Test
	void test1CreateOffer() throws Exception {
		OfferRequestDTO offerRequestDTO = getOfferReqObj();
		OfferResponseDTO offerResponeDTO = getOfferResObj();

		lenient().when(offerService.addOffer(1L, offerRequestDTO)).thenReturn(offerResponeDTO);

		mockMvc.perform(post("/api/v1/hotels/1/offers").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(offerRequestDTO)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());

		assertEquals(offerRequestDTO.getOfferCode(), offerResponeDTO.getOfferCode());
	}

	@Test
	void test2GetAllOffers() throws Exception {

		List<OfferResponseDTO> offerResponeObj = new ArrayList<>();
		offerResponeObj.add(getOfferResObj());

		lenient().when(offerService.getAllOffers(1L)).thenReturn(offerResponeObj);

		mockMvc.perform(get("/api/v1/hotels/1/offers").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

		assertNotNull(offerResponeObj);
	}

	@Test
	void test3UpdateOffer() throws Exception {

		OfferRequestDTO offerReq = getOfferReqObj();
		OfferResponseDTO offerRes = getOfferResObj();

		lenient().when(offerService.updateOffer(1L, 1, offerReq)).thenReturn(offerRes);

		mockMvc.perform(put("/api/v1/hotels/1/offers/1").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(offerReq)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

		assertEquals(offerReq.getOfferCode(), offerRes.getOfferCode());
	}

	@Test
	void test4DeleteOffer() throws Exception {

		OfferResponseDTO offerRes = getOfferResObj();

		lenient().when(offerService.deleteOffer(1L)).thenReturn(offerRes);

		mockMvc.perform(delete("/api/v1/hotels/1/offers/1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		assertNotNull(offerRes);
	}
}
