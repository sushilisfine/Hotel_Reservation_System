package com.myhotel.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.myhotel.constants.HotelConstants;
import com.myhotel.domain.Reservation;
import com.myhotel.dto.request.ReservationRequestDTO;
import com.myhotel.dto.response.ReservationResponseDTO;
import com.myhotel.exception.CustomException;
import com.myhotel.feign.dto.request.RoomUpdateRequestDTO;
import com.myhotel.feign.dto.response.CategoryResponseDTO;
import com.myhotel.feign.dto.response.OfferResponseDTO;
import com.myhotel.feign.dto.response.RoomCreateResponseDTO;
import com.myhotel.feignservice.HotelFeignService;
import com.myhotel.repository.ReservationRepository;
import com.myhotel.service.ReservationService;
import com.myhotel.utility.ExceptionUtility;

@Service
public class ReservationServiceImpl implements ReservationService {

	private HotelFeignService hotelFeignService;
	private ExceptionUtility exUtil;
	private ReservationRepository reservationRepository;

	public ReservationServiceImpl(HotelFeignService hotelFeignService, ReservationRepository reservationRepository,
			ExceptionUtility exUtil) {
		this.hotelFeignService = hotelFeignService;
		this.reservationRepository = reservationRepository;
		this.exUtil = exUtil;
	}

	Logger logger = LoggerFactory.getLogger(ReservationServiceImpl.class);

	@Override
	public ReservationResponseDTO createReservation(ReservationRequestDTO reservationReqObj) {

		LocalDate startDate = reservationReqObj.getStartDate();
		LocalDate endDate = reservationReqObj.getEndDate();

		List<LocalDate> requestedDates = getListOfDates(startDate, endDate);
		RoomCreateResponseDTO room = null;
		OfferResponseDTO offerResponseDTO = null;
		CategoryResponseDTO category = null;

		try {
			room = getFromAvailableRooms(reservationReqObj.getHotelId(), startDate, endDate,
					reservationReqObj.getCategoryId());
		} catch (Exception e) {
			throw new CustomException(exUtil.notFoundMessage(HotelConstants.ROOM));
		}
		if (room == null)
			throw new CustomException(exUtil.notFoundMessage(HotelConstants.ROOM));

		try {
			offerResponseDTO = getOffer(reservationReqObj.getHotelId(), reservationReqObj.getCategoryId());
		} catch (Exception e) {
			throw new CustomException(exUtil.notFoundMessage(HotelConstants.OFFER));
		}
		if (offerResponseDTO == null)
			throw new CustomException(exUtil.notFoundMessage(HotelConstants.OFFER));

		updateBookingDates(requestedDates, reservationReqObj.getHotelId(), room.getId());

		try {
			category = getCategory(reservationReqObj.getHotelId(), reservationReqObj.getCategoryId());
		} catch (Exception e) {
			throw new CustomException(exUtil.notFoundMessage(HotelConstants.CATEGORY));
		}
		if (category == null)
			throw new CustomException(exUtil.notFoundMessage(HotelConstants.CATEGORY));

		Double totalCharges = calculateReservationCharges(category.getCharges(), requestedDates.size(),
				offerResponseDTO.getValue());

		Reservation reservation = createEntry(reservationReqObj.getHotelId(), reservationReqObj.getCategoryId(),
				startDate, endDate, totalCharges, offerResponseDTO.getId(), room.getId());

		return converteEntityToDTO(reservation);
	}

	private Reservation createEntry(Long hotelId, Long categoryId, LocalDate startDate, LocalDate endDate,
			Double totalCharges, Long offerId, Long roomId) {

		Reservation reservation = new Reservation();
		reservation.setCategoryId(categoryId);
		reservation.setCharges(totalCharges);
		reservation.setStartDate(startDate);
		reservation.setEndDate(endDate);
		reservation.setGuestId(null);
		reservation.setHotelId(hotelId);
		reservation.setOfferId(offerId);
		reservation.setRoomId(roomId);

		reservationRepository.save(reservation);

		return reservation;

	}

	private Double calculateReservationCharges(Double perRoomCharges, int days, Double offerAmount) {
		return (perRoomCharges - offerAmount) * days;
	}

	private OfferResponseDTO getOffer(Long hotelId, Long categoryId) {
		return hotelFeignService.getOffer(hotelId, categoryId).getBody();
	}

	private CategoryResponseDTO getCategory(Long hotelId, Long categoryId) {
		return hotelFeignService.getCategory(hotelId, categoryId).getBody();
	}

	private void updateBookingDates(List<LocalDate> requestedDates, Long hotelId, Long roomId) {
		RoomUpdateRequestDTO roomRequestDTO = new RoomUpdateRequestDTO();
		roomRequestDTO.setBookedDates(requestedDates);
		try {
			hotelFeignService.updateBooking(hotelId, roomId, roomRequestDTO);
		} catch (Exception e) {
			throw new CustomException("Please try again with other dates");
		}
	}

	private List<LocalDate> getListOfDates(LocalDate startDate, LocalDate endDate) {

		if (startDate.isAfter(endDate)) {
			logger.error("Start Date: {} is greater than: {}", startDate, endDate);
			throw new CustomException("Start Date should be less than End Date, Please check");
		}

		Stream<LocalDate> dates = startDate.datesUntil(endDate.plusDays(1));

		return dates.collect(Collectors.toList());
	}

	private RoomCreateResponseDTO getFromAvailableRooms(Long hotelId, LocalDate startDate, LocalDate endDate,
			Long categoryId) {

		List<RoomCreateResponseDTO> rooms = hotelFeignService
				.getAvailableRooms(hotelId, startDate.toString(), endDate.toString(), categoryId).getBody();

		RoomCreateResponseDTO room = null;

		if (rooms != null && !rooms.isEmpty())
			room = rooms.get(0);

		return room;
	}

	private ReservationResponseDTO converteEntityToDTO(Reservation reservation) {

		return ReservationResponseDTO.builder().id(reservation.getId()).categoryId(reservation.getCategoryId())
				.charges(reservation.getCharges()).endDate(reservation.getEndDate()).guestId(reservation.getGuestId())
				.hotelId(reservation.getHotelId()).offerId(reservation.getOfferId()).roomId(reservation.getRoomId())
				.startDate(reservation.getStartDate()).build();

	}

}
