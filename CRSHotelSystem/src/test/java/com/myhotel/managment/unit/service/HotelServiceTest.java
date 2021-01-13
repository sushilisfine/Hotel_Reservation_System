package com.myhotel.managment.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;

import com.myhotel.managment.domain.Hotel;
import com.myhotel.managment.dto.request.HotelRequestDTO;
import com.myhotel.managment.dto.response.HotelResponseDTO;
import com.myhotel.managment.repository.HotelRepository;
import com.myhotel.managment.service.impl.HotelServiceImpl;

@ExtendWith(MockitoExtension.class)
@PropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
class HotelServiceTest {

	@InjectMocks
	private HotelServiceImpl hotelService;

	@Mock
	private HotelRepository hotelRepository;

	private Hotel hotelObj() {
		Hotel hotel = new Hotel();
		hotel.setId(1L);
		hotel.setAddress("Nagpur");
		hotel.setContact(9999999999L);
		hotel.setHotelCode(1L);
		return hotel;
	}

	private HotelRequestDTO hotelRequestDTOObj() {
		HotelRequestDTO hotelRequestDTO = new HotelRequestDTO();
		hotelRequestDTO.setAddress("Nagpur");
		hotelRequestDTO.setContact(9999999999L);
		hotelRequestDTO.setHotelCode(1L);
		return hotelRequestDTO;
	}

	private HotelResponseDTO hotelResponseDTOObj() {
		HotelResponseDTO hotelResponseDTO = new HotelResponseDTO();
		hotelResponseDTO.setId(1L);
		hotelResponseDTO.setAddress("Nagpur");
		hotelResponseDTO.setContact(9999999999L);
		hotelResponseDTO.setHotelCode(1L);
		return hotelResponseDTO;
	}

	@Test
	void test1AddHotel1() {
		Hotel hotelObjDb = hotelObj();
		HotelRequestDTO hotelRequestDTO = hotelRequestDTOObj();

		lenient().doReturn(null).when(hotelRepository).findByHotelCode(hotelRequestDTO.getHotelCode());
		lenient().doReturn(hotelObjDb).when(hotelRepository).save(Mockito.any(Hotel.class));
		HotelResponseDTO createdHotel = hotelService.createHotel(hotelRequestDTO);

		assertEquals(hotelRequestDTO.getAddress(), createdHotel.getAddress());
		assertEquals(hotelRequestDTO.getContact(), createdHotel.getContact());
		assertEquals(hotelRequestDTO.getHotelCode(), createdHotel.getHotelCode());
		assertNotNull(createdHotel.getId());
	}

	@Test
	void test1AddHotel2() {
		Hotel hotelObjDb = hotelObj();
		HotelRequestDTO hotelRequestDTO = hotelRequestDTOObj();

		lenient().doReturn(hotelObjDb).when(hotelRepository).findByHotelCode(hotelRequestDTO.getHotelCode());

		HotelResponseDTO createdHotel = hotelService.createHotel(hotelRequestDTO);

		assertNull(createdHotel);
	}

	@Test
	void test2UpdateHotel() {
		Hotel hotelObj = hotelObj();
		HotelRequestDTO hotelRequestDTO = hotelRequestDTOObj();

		lenient().doReturn(hotelObj).when(hotelRepository).findByHotelCode(hotelRequestDTO.getHotelCode());
		lenient().doReturn(hotelObj).when(hotelRepository).save(Mockito.any(Hotel.class));
		HotelResponseDTO updatedHotel = hotelService.updateHotel(1L, hotelRequestDTO);

		assertEquals(hotelRequestDTO.getAddress(), updatedHotel.getAddress());
		assertEquals(hotelRequestDTO.getContact(), updatedHotel.getContact());
		assertEquals(hotelRequestDTO.getHotelCode(), updatedHotel.getHotelCode());
		assertNotNull(updatedHotel.getId());
	}

	@Test
	void test3GetAllHotels() {
		List<HotelResponseDTO> hotelResponseDTO = new ArrayList<>();
		hotelResponseDTO.add(hotelResponseDTOObj());

		List<Hotel> hotelObj = new ArrayList<>();
		hotelObj.add(hotelObj());
		lenient().doReturn(hotelObj).when(hotelRepository).findAll();
		List<HotelResponseDTO> createdHotel = hotelService.getAllHotels();

		assertNotNull(createdHotel.get(0).getAddress());
		assertNotNull(createdHotel.get(0).getContact());
		assertNotNull(createdHotel.get(0).getHotelCode());
		assertNotNull(createdHotel.get(0).getId());
	}
}
