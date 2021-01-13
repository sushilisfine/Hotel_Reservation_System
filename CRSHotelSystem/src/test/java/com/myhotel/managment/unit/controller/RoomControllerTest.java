package com.myhotel.managment.unit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import com.myhotel.managment.controller.RoomController;
import com.myhotel.managment.dto.request.RoomRequestDTO;
import com.myhotel.managment.dto.response.RoomResponseDTO;
import com.myhotel.managment.service.RoomService;

class RoomControllerTest extends AbstractTest {

	private MockMvc mockMvc;

	@Mock
	private RoomService roomService;

	@InjectMocks
	private RoomController roomController;

	@InjectMocks
	private HotelController hotelController;

	@BeforeEach
	public void setup() {

		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(roomController, hotelController).build();
	}

	private RoomRequestDTO roomReqObj() {
		RoomRequestDTO room = new RoomRequestDTO();
		room.setRoomCode(1);
		room.setRoomCategory("Single");
		room.setIsAvailable(true);
		room.setCharges(200.00);
		return room;
	}

	private RoomResponseDTO roomResObj() {
		RoomResponseDTO room = new RoomResponseDTO();
		room.setRoomCode(1);
		room.setRoomCategory("Single");
		room.setIsAvailable(true);
		room.setCharges(200.00);
		return room;
	}

	@Test
	void test1SaveHotel() throws Exception {

		RoomRequestDTO roomReq = roomReqObj();
		RoomResponseDTO roomRes = roomResObj();

		lenient().when(roomService.addRoom(1L, roomReq)).thenReturn(roomRes);

		mockMvc.perform(post("/api/v1/hotels/1/rooms").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(roomReq)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

		assertEquals(roomReq.getRoomCode(), roomRes.getRoomCode());
	}

	@Test
	void test1UpdateRoom() throws Exception {

		RoomRequestDTO roomReq = roomReqObj();
		RoomResponseDTO roomRes = roomResObj();

		lenient().when(roomService.updateRoom(1L, 1, roomReq)).thenReturn(roomRes);

		mockMvc.perform(put("/api/v1/hotels/1/rooms/1").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(roomReq)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

		assertEquals(roomReq.getRoomCode(), roomRes.getRoomCode());
	}

	@Test
	void test1GetAllRooms() throws Exception {

		List<RoomResponseDTO> roomResObj = new ArrayList<>();
		roomResObj.add(roomResObj());

		lenient().when(roomService.getAllRooms(1L)).thenReturn(roomResObj);

		mockMvc.perform(get("/api/v1/hotels/1/rooms").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

		assertNotNull(roomResObj);
	}

}
