package com.myhotel.service;

import com.myhotel.dto.request.ReservationRequestDTO;
import com.myhotel.dto.response.ReservationResponseDTO;

public interface ReservationService {

	ReservationResponseDTO createReservation(ReservationRequestDTO reservationReqObj);

}
