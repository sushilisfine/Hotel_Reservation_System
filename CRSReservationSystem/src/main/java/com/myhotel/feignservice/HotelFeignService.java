package com.myhotel.feignservice;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.myhotel.feign.dto.request.RoomUpdateRequestDTO;
import com.myhotel.feign.dto.response.CategoryResponseDTO;
import com.myhotel.feign.dto.response.HotelResponseDTO;
import com.myhotel.feign.dto.response.OfferResponseDTO;
import com.myhotel.feign.dto.response.RoomCreateResponseDTO;
import com.myhotel.feign.dto.response.RoomUpdateResponseDTO;

@FeignClient(name = "hotel-service", path = "api/v1")
public interface HotelFeignService {

	@GetMapping("/hotels")
	HotelResponseDTO getAllHotels();

	@PutMapping(value = "/hotels/{hotel_id}/rooms/{room_id}")
	public ResponseEntity<RoomUpdateResponseDTO> updateBooking(@PathVariable("hotel_id") Long hotelId,
			@PathVariable("room_id") Long roomId, @RequestBody RoomUpdateRequestDTO roomUpdateRequestDTO);

	@GetMapping(value = "/hotels/{hotel_id}/rooms")
	public ResponseEntity<List<RoomCreateResponseDTO>> getAllRooms(@PathVariable("hotel_id") Long hotelId);

	@GetMapping(value = "/hotels/{hotel_id}/rooms/availability")
	public ResponseEntity<List<RoomCreateResponseDTO>> getAvailableRooms(
			@PathVariable(required = true, name = "hotel_id") Long hotelId, @RequestParam String from,
			@RequestParam String to, @RequestParam Long categoryId);

	@GetMapping(value = "/hotels/{hotel_id}/offers")
	public ResponseEntity<OfferResponseDTO> getOffer(@PathVariable("hotel_id") Long hotelId,
			@RequestParam("category_id") Long categoryId);

	@GetMapping(value = "/hotels/{hotel_id}/categories/{category_id}")
	public ResponseEntity<CategoryResponseDTO> getCategory(@PathVariable("hotel_id") Long hotelId,
			@PathVariable("category_id") Long categoryId);
}
