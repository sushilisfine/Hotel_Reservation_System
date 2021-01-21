package com.myhotel.managment.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.myhotel.managment.domain.Category;
import com.myhotel.managment.domain.Hotel;
import com.myhotel.managment.domain.Room;
import com.myhotel.managment.dto.RoomDTO;
import com.myhotel.managment.repository.CategoryRepository;
import com.myhotel.managment.repository.HotelRepository;
import com.myhotel.managment.repository.RoomRepository;
import com.myhotel.managment.service.RoomService;

@Service
public class RoomServiceImpl implements RoomService {

	private RoomRepository roomRepository;
	private HotelRepository hotelRepository;
	private CategoryRepository categoryRepository;

	public RoomServiceImpl(RoomRepository roomRepository, HotelRepository hotelRepository,
			CategoryRepository categoryRepository) {
		this.roomRepository = roomRepository;
		this.hotelRepository = hotelRepository;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public RoomDTO add(RoomDTO roomDTO) {
		Room room = converteDTOToEntity(roomDTO);
		return convertEntityToDTO(roomRepository.save(room));
	}

	@Override
	public RoomDTO update(RoomDTO roomDTO) {
		addBookingDates(roomDTO);
		Room room = converteDTOToEntity(roomDTO);
		return convertEntityToDTO(roomRepository.save(room));
	}

	// Adding new booking dates
	private RoomDTO addBookingDates(RoomDTO room) {
		if (room.getBookedDates() != null) {
			Room roomDb = get(room.getId());
			room.getBookedDates().addAll(roomDb.getBookedDates());
		}
		return room;
	}

	@Override
	public List<RoomDTO> getAll(Long hotelId) {
		Hotel hotel = Hotel.builder().id(hotelId).build();
		Optional<List<Room>> rooms = roomRepository.findAllByHotel(hotel);
		List<Room> roomsList = rooms.isPresent() ? rooms.get() : new ArrayList<>();
		return convertEntityToDTO(roomsList);
	}

	private Room converteDTOToEntity(RoomDTO roomDTO) {
		Hotel hotel = Hotel.builder().id(roomDTO.getHotelId()).build();
		Category category = Category.builder().hotel(hotel).id(roomDTO.getCategoryId()).build();
		return Room.builder().id(roomDTO.getId()).hotel(hotel).category(category).bookedDates(roomDTO.getBookedDates())
				.build();
	}

	private RoomDTO convertEntityToDTO(Room room) {
		return RoomDTO.builder().id(room.getId()).categoryId(room.getCategory().getId())
				.hotelId(room.getHotel().getId()).bookedDates(room.getBookedDates()).build();
	}

	private List<RoomDTO> convertEntityToDTO(List<Room> rooms) {
		List<RoomDTO> roomCreateResponseDTO = new ArrayList<>();
		rooms.forEach(room -> roomCreateResponseDTO.add(convertEntityToDTO(room)));
		return roomCreateResponseDTO;
	}

	@Override
	public List<RoomDTO> getByParams(Long hotelId, LocalDate from, LocalDate to, Long categoryId) {
		List<Room> rooms = get(hotelId, categoryId);
		List<Room> availableRooms = filterAvailableRooms(rooms, from, to);
		return convertEntityToDTO(availableRooms);
	}

	private List<Room> filterAvailableRooms(List<Room> rooms, LocalDate from, LocalDate to) {

		List<LocalDate> requestedDates = getListOfDates(from, to);

		rooms = rooms.stream().filter(room -> {
			List<LocalDate> clonedRequestedDates = new ArrayList<>(requestedDates);
			clonedRequestedDates.removeAll(room.getBookedDates());
			return requestedDates.size() == clonedRequestedDates.size();
		}).collect(Collectors.toList());

		return rooms;
	}

	@Override
	public List<LocalDate> getListOfDates(LocalDate from, LocalDate to) {
		Stream<LocalDate> dates = from.datesUntil(to.plusDays(1));
		return dates.collect(Collectors.toList());
	}

	@Override
	public Hotel getHotel(Long hotelId) {
		Optional<Hotel> hotel = hotelRepository.findById(hotelId);
		return hotel.isPresent() ? hotel.get() : new Hotel();
	}

	@Override
	public Category getCategory(Long categoryId) {
		Optional<Category> category = categoryRepository.findById(categoryId);
		return category.isPresent() ? category.get() : new Category();
	}

	@Override
	public Room get(Long roomId) {
		Optional<Room> room = roomRepository.findById(roomId);
		return room.isPresent() ? room.get() : new Room();
	}

	@Override
	public List<Room> get(Long hotelId, Long categoryId) {
		Hotel hotel = Hotel.builder().id(hotelId).build();
		Category category = Category.builder().id(categoryId).hotel(hotel).build();
		Optional<List<Room>> rooms = roomRepository.findAllByHotelAndCategory(hotel, category);
		return rooms.isPresent() ? rooms.get() : new ArrayList<>();
	}

}
