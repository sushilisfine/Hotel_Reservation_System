package com.myhotel.managment.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.myhotel.managment.domain.Hotel;
import com.myhotel.managment.domain.Room;
import com.myhotel.managment.dto.request.RoomRequestDTO;
import com.myhotel.managment.dto.response.RoomResponseDTO;
import com.myhotel.managment.repository.HotelRepository;
import com.myhotel.managment.repository.RoomRepository;
import com.myhotel.managment.service.RoomService;

@Service
public class RoomServiceImpl implements RoomService {

	Logger logger = LoggerFactory.getLogger(RoomServiceImpl.class);

	private RoomRepository roomRepository;
	private HotelRepository hotelRepository;

	public RoomServiceImpl(RoomRepository roomRepository, HotelRepository hotelRepository) {
		this.roomRepository = roomRepository;
		this.hotelRepository = hotelRepository;
	}

	@Override
	public RoomResponseDTO addRoom(Long hotelCode, RoomRequestDTO roomDTO) {

		Room roomDb = roomRepository.findByRoomCode(roomDTO.getRoomCode());

		if (roomDb != null) {

			logger.error("Room with Room Code : {} , already exists.", roomDTO.getRoomCode());
			return null;

		}

		Hotel hotel = getHotelFromHotelCode(hotelCode);

		Room room = converteDTOToEntity(roomDTO);
		room.setHotel(hotel);
		return converteEntityToDTO(roomRepository.save(room));

	}

	@Override
	public RoomResponseDTO updateRoom(Long hotelCode, Integer roomCode, RoomRequestDTO roomRequestDTO) {

		Hotel hotelDb = hotelRepository.findByHotelCode(hotelCode);

		Room room = roomRepository.findByRoomCodeAndHotel(roomCode, hotelDb);

		return updateRoomFromDTO(room, roomRequestDTO);
	}

	private RoomResponseDTO updateRoomFromDTO(Room room, RoomRequestDTO roomRequestDTO) {

		room.setCharges(roomRequestDTO.getCharges());
		room.setIsAvailable(roomRequestDTO.getIsAvailable());
		room.setRoomCategory(roomRequestDTO.getRoomCategory());

		return converteEntityToDTO(room);

	}

	@Override
	public List<RoomResponseDTO> getAllRooms(Long hotelCode) {
		Hotel hotel = getHotelFromHotelCode(hotelCode);
		List<Room> rooms = roomRepository.findAllByHotel(hotel);
		List<RoomResponseDTO> roomResponseDTO = new ArrayList<>();

		rooms.forEach(room -> {
			roomResponseDTO.add(converteEntityToDTO(room));

		});
		return roomResponseDTO;
	}

	private Room converteDTOToEntity(RoomRequestDTO roomDTO) {

		return Room.builder().charges(roomDTO.getCharges()).isAvailable(roomDTO.getIsAvailable())
				.roomCategory(roomDTO.getRoomCategory()).roomCode(roomDTO.getRoomCode()).build();

	}

	private RoomResponseDTO converteEntityToDTO(Room room) {

		return RoomResponseDTO.builder().charges(room.getCharges()).isAvailable(room.getIsAvailable())
				.roomCategory(room.getRoomCategory()).id(room.getId()).roomCode(room.getRoomCode()).build();

	}

	private Hotel getHotelFromHotelCode(Long hotelCode) {
		return hotelRepository.findByHotelCode(hotelCode);
	}

}
