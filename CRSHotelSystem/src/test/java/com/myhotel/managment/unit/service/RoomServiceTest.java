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
import com.myhotel.managment.domain.Room;
import com.myhotel.managment.dto.request.RoomRequestDTO;
import com.myhotel.managment.dto.response.RoomResponseDTO;
import com.myhotel.managment.repository.HotelRepository;
import com.myhotel.managment.repository.RoomRepository;
import com.myhotel.managment.service.impl.RoomServiceImpl;

@ExtendWith(MockitoExtension.class)
@PropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
class RoomServiceTest {

	@InjectMocks
	private RoomServiceImpl roomService;

	@Mock
	private RoomRepository roomRepository;

	@Mock
	private HotelRepository hotelRepository;

	private RoomRequestDTO roomReqObj() {
		RoomRequestDTO room = new RoomRequestDTO();
		room.setRoomCode(1);
		room.setRoomCategory("Single");
		room.setIsAvailable(true);
		room.setCharges(200.00);
		return room;
	}

	private Room roomObj() {
		Room room = new Room();
		room.setRoomCode(1);
		room.setRoomCategory("Single");
		room.setIsAvailable(true);
		room.setCharges(200.00);
		return room;
	}

	private Hotel hotelObj() {
		Hotel hotel = new Hotel();
		hotel.setAddress("Nagpur");
		hotel.setContact(9999999999L);
		hotel.setHotelCode(1L);
		return hotel;
	}

	@Test
	void test1AddRoom() {
		RoomRequestDTO roomRequestDTO = roomReqObj();
		Room room = roomObj();

		doReturn(null).when(roomRepository).findByRoomCode(roomRequestDTO.getRoomCode());
		doReturn(room).when(roomRepository).save(Mockito.any(Room.class));

		RoomResponseDTO createdRoom = roomService.addRoom(1L, roomRequestDTO);

		assertEquals(createdRoom.getCharges(), roomRequestDTO.getCharges());
		assertEquals(createdRoom.getIsAvailable(), roomRequestDTO.getIsAvailable());
		assertEquals(createdRoom.getRoomCategory(), roomRequestDTO.getRoomCategory());
		assertEquals(createdRoom.getRoomCode(), roomRequestDTO.getRoomCode());
	}

	@Test
	void test2AddRoom() {
		RoomRequestDTO roomRequestDTO = roomReqObj();
		Room room = roomObj();

		doReturn(room).when(roomRepository).findByRoomCode(roomRequestDTO.getRoomCode());
		lenient().doReturn(room).when(roomRepository).save(Mockito.any(Room.class));

		RoomResponseDTO createdRoom = roomService.addRoom(1L, roomRequestDTO);

		assertNull(createdRoom);
	}

	@Test
	void test3UpdateRoom() {
		RoomRequestDTO roomRequestDTO = roomReqObj();
		Room room = roomObj();
		Hotel hotel = hotelObj();
		doReturn(hotel).when(hotelRepository).findByHotelCode(1L);
		lenient().doReturn(room).when(roomRepository).findByRoomCodeAndHotel(1, hotel);

		RoomResponseDTO updatedRoom = roomService.updateRoom(1L, 1, roomRequestDTO);

		assertEquals(updatedRoom.getCharges(), roomRequestDTO.getCharges());
		assertEquals(updatedRoom.getIsAvailable(), roomRequestDTO.getIsAvailable());
		assertEquals(updatedRoom.getRoomCategory(), roomRequestDTO.getRoomCategory());
		assertEquals(updatedRoom.getRoomCode(), roomRequestDTO.getRoomCode());
	}

}
