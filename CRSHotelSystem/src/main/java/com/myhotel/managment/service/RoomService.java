package com.myhotel.managment.service;

import java.util.List;

import com.myhotel.managment.dto.request.RoomRequestDTO;
import com.myhotel.managment.dto.response.RoomResponseDTO;

public interface RoomService {

	RoomResponseDTO addRoom(Long hotelCode, RoomRequestDTO roomDTO);

	RoomResponseDTO updateRoom(Long hotelCode, Integer roomCode, RoomRequestDTO roomRequestDTO);

	List<RoomResponseDTO> getAllRooms(Long hotelCode);

	List<RoomResponseDTO> getAvailableRooms();

}
