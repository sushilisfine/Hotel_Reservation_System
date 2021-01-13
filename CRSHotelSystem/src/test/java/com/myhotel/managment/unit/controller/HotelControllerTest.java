package com.myhotel.managment.unit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.myhotel.managment.controller.HotelController;
import com.myhotel.managment.dto.request.HotelRequestDTO;
import com.myhotel.managment.dto.response.HotelResponseDTO;
import com.myhotel.managment.service.HotelService;

class HotelControllerTest extends AbstractTest {

	private MockMvc mockMvc;

	@Mock
	private HotelService hotelService;

	@InjectMocks
	private HotelController hotelController;

	@BeforeEach
	public void setup() {

		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(hotelController).build();
	}

	private HotelRequestDTO hotelRequestObj() {
		HotelRequestDTO hotel = new HotelRequestDTO();
		hotel.setAddress("Nagpur");
		hotel.setContact(9999999999L);
		hotel.setHotelCode(1L);
		return hotel;
	}

	private HotelResponseDTO hotelResponseObj() {
		HotelResponseDTO hotel = new HotelResponseDTO();
		hotel.setAddress("Nagpur");
		hotel.setContact(9999999999L);
		hotel.setHotelCode(1L);
		return hotel;
	}

	@Test
	void test1SaveHotel() throws Exception {

		HotelRequestDTO hotelReqObj = hotelRequestObj();
		HotelResponseDTO hotelResObj = hotelResponseObj();

		lenient().doReturn(hotelResObj).when(hotelService).createHotel(hotelReqObj);

		mockMvc.perform(post("/api/v1/hotels").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(hotelReqObj)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

		assertEquals(hotelReqObj.getHotelCode(), hotelResObj.getHotelCode());

	}

	@Test
	void test1UpdateHotel() throws Exception {

		HotelRequestDTO hotelReqObj = hotelRequestObj();
		HotelResponseDTO hotelResObj = hotelResponseObj();

		lenient().doReturn(hotelResObj).when(hotelService).updateHotel(1L, hotelReqObj);

		mockMvc.perform(put("/api/v1/hotels/1").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(hotelReqObj)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

		assertEquals(hotelReqObj.getHotelCode(), hotelResObj.getHotelCode());

	}

	@Test
	void test1GetAllHotels() throws Exception {

		List<HotelResponseDTO> hotelResObj = new ArrayList<>();
		hotelResObj.add(hotelResponseObj());

		lenient().doReturn(hotelResObj).when(hotelService).getAllHotels();

		mockMvc.perform(get("/api/v1/hotels").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}

}
