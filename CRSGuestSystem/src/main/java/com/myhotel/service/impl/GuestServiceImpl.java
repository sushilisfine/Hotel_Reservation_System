package com.myhotel.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.myhotel.domain.Guest;
import com.myhotel.dto.request.GuestRequestDTO;
import com.myhotel.dto.response.GuestResponseDTO;
import com.myhotel.repository.GuestRepository;
import com.myhotel.service.GuestService;

@Service
public class GuestServiceImpl implements GuestService {

	private GuestRepository guestRepository;

	Logger logger = LoggerFactory.getLogger(GuestServiceImpl.class);

	public GuestServiceImpl(GuestRepository guestRepository) {
		this.guestRepository = guestRepository;
	}

	@Override
	public GuestResponseDTO createGuest(GuestRequestDTO guestDTO) {

		Guest guestDb = guestRepository.findByGuestCode(guestDTO.getGuestCode());

		if (guestDb != null) {
			logger.error("Guest with guest code : {} , already exists.", guestDTO.getGuestCode());
			return null;
		}
		Guest guest = converteDTOToEntity(guestDTO);

		return converteEntityToDTO(guestRepository.save(guest));

	}

	@Override
	public GuestResponseDTO updateGuest(Long guestCode, GuestRequestDTO guestRequestDTO) {
		Guest guestDb = getGuestFromGuestCode(guestCode);

		updateGuestFromDTO(guestDb, guestRequestDTO);
		guestRepository.save(guestDb);

		return converteEntityToDTO(guestDb);
	}

	private Guest updateGuestFromDTO(Guest guest, GuestRequestDTO guestRequestDTO) {

		guest.setName(guestRequestDTO.getName());
		guest.setEmail(guestRequestDTO.getEmail());
		guest.setContact(guestRequestDTO.getContact());
		guest.setGuestCode(guestRequestDTO.getGuestCode());

		return guest;

	}

	@Override
	public List<GuestResponseDTO> getAllGuests() {

		List<Guest> guests = guestRepository.findAll();
		List<GuestResponseDTO> guestResponseDTO = new ArrayList<>();

		guests.forEach(guest -> {
			guestResponseDTO.add(converteEntityToDTO(guest));

		});
		return guestResponseDTO;
	}

	private Guest converteDTOToEntity(GuestRequestDTO guestDTO) {

		return Guest.builder().email(guestDTO.getEmail()).contact(guestDTO.getContact()).name(guestDTO.getName())
				.guestCode(guestDTO.getGuestCode()).build();

	}

	private GuestResponseDTO converteEntityToDTO(Guest guest) {

		return GuestResponseDTO.builder().contact(guest.getContact()).name(guest.getName()).email(guest.getEmail())
				.guestCode(guest.getGuestCode()).id(guest.getId()).build();

	}

	private Guest getGuestFromGuestCode(Long guestCode) {
		return guestRepository.findByGuestCode(guestCode);
	}

	@Override
	public GuestResponseDTO getGuest(Long guestCode) {
		return converteEntityToDTO(getGuestFromGuestCode(guestCode));
	}

}
