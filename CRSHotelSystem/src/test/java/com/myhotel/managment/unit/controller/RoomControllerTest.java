package com.myhotel.managment.unit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.myhotel.managment.dto.request.RoomRequestDTO;
import com.myhotel.managment.dto.response.RoomResponseDTO;
import com.myhotel.managment.service.RoomService;

class RoomControllerTest extends AbstractTest {

	@MockBean
	private RoomService roomService;

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

		doReturn(roomRes).when(roomService.addRoom(1L, roomReq));

		mvc.perform(post("/api/v1/hotels/1/rooms").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(roomReq)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

		assertEquals(roomReq.getRoomCode(), roomRes.getRoomCode());
	}

	@Test
	void test1UpdateRoom() throws Exception {

		RoomRequestDTO roomReq = roomReqObj();
		RoomResponseDTO roomRes = roomResObj();

		doReturn(roomRes).when(roomService.updateRoom(1L, 1, roomReq));

		mvc.perform(put("/api/v1/hotels/1/rooms/1").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(roomReq)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

		assertEquals(roomReq.getRoomCode(), roomRes.getRoomCode());
	}

	@Test
	void test1GetAllRooms() throws Exception {

		List<RoomResponseDTO> roomResObj = new ArrayList<>();
		roomResObj.add(roomResObj());

		doReturn(roomResObj).when(roomService.getAllRooms(1L));

		mvc.perform(get("/api/v1/hotels/1/rooms").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}

}
