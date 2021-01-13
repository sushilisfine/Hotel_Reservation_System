package com.myhotel.unit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.myhotel.dto.request.GuestRequestDTO;
import com.myhotel.dto.response.GuestResponseDTO;
import com.myhotel.service.GuestService;

class GuestControllerTest extends AbstractTest {

	@MockBean
	private GuestService guestService;

	private GuestRequestDTO guestRequestObj() {
		GuestRequestDTO guest = new GuestRequestDTO();
		guest.setEmail("test@test.com");
		guest.setName("test");
		guest.setContact(9999999999L);
		guest.setGuestCode(1L);
		return guest;
	}

	private GuestResponseDTO guestResponseObj() {
		GuestResponseDTO guest = new GuestResponseDTO();
		guest.setEmail("test@test.com");
		guest.setName("test");
		guest.setContact(9999999999L);
		guest.setGuestCode(1L);
		return guest;
	}

	@Test
	void test1SaveGuest() throws Exception {

		GuestRequestDTO guestReqObj = guestRequestObj();
		GuestResponseDTO guestResObj = guestResponseObj();

		doReturn(guestResObj).when(guestService.createGuest(guestReqObj));

		mvc.perform(post("/api/v1/guests").contentType(MediaType.APPLICATION_JSON).content(asJsonString(guestReqObj))
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

		assertEquals(guestReqObj.getGuestCode(), guestResObj.getGuestCode());

	}

	@Test
	void test1UpdateGuest() throws Exception {

		GuestRequestDTO guestReqObj = guestRequestObj();
		GuestResponseDTO guestResObj = guestResponseObj();

		doReturn(guestResObj).when(guestService.updateGuest(1L, guestReqObj));

		mvc.perform(put("/api/v1/guests/1").contentType(MediaType.APPLICATION_JSON).content(asJsonString(guestReqObj))
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

		assertEquals(guestReqObj.getGuestCode(), guestResObj.getGuestCode());

	}

	@Test
	void test1GetAllGuests() throws Exception {

		List<GuestResponseDTO> guestResObj = new ArrayList<>();
		guestResObj.add(guestResponseObj());

		doReturn(guestResObj).when(guestService.getAllGuests());

		mvc.perform(get("/api/v1/guests").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

		assertNotNull(guestResObj);

	}

	@Test
	void test1GetGuestByGuestCode() throws Exception {

		GuestResponseDTO guestResObj = guestResponseObj();

		doReturn(guestResObj).when(guestService.getGuest(1L));

		mvc.perform(get("/api/v1/guests/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

		assertNotNull(guestResObj);
		assertEquals(1, guestResObj.getId());

	}

}
