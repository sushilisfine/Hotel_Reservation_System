package com.myhotel.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.myhotel.domain.Guest;
import com.myhotel.dto.GuestDTO;
import com.myhotel.repository.GuestRepository;
import com.myhotel.service.GuestService;

@Service
public class GuestServiceImpl implements GuestService {

	private GuestRepository guestRepository;

	public GuestServiceImpl(GuestRepository guestRepository) {
		this.guestRepository = guestRepository;
	}

	@Override
	public GuestDTO create(GuestDTO guestDTO) {
		Guest guest = converteDTOToEntity(guestDTO);
		return converteEntityToDTO(guestRepository.save(guest));
	}

	@Override
	public GuestDTO update(GuestDTO guestRequestDTO) {
		Guest guest = converteDTOToEntity(guestRequestDTO);
		guestRepository.save(guest);

		return converteEntityToDTO(guest);
	}

	@Override
	public GuestDTO get(Long guestId) {
		Guest guest = getGuestById(guestId);
		return converteEntityToDTO(guest);
	}

	@Override
	public List<GuestDTO> getAll() {

		List<Guest> guests = guestRepository.findAll();

		return converteEntityToDTO(guests);
	}

	private Guest converteDTOToEntity(GuestDTO guestDTO) {

		return Guest.builder().email(guestDTO.getEmail()).id(guestDTO.getId()).contact(guestDTO.getContact())
				.name(guestDTO.getName()).build();
	}

	private GuestDTO converteEntityToDTO(Guest guest) {

		GuestDTO guestResponseDTO = new GuestDTO();

		if (guest != null)
			guestResponseDTO = GuestDTO.builder().contact(guest.getContact()).name(guest.getName())
					.email(guest.getEmail()).id(guest.getId()).build();

		return guestResponseDTO;
	}

	private List<GuestDTO> converteEntityToDTO(List<Guest> guests) {

		List<GuestDTO> guestResponseDTO = new ArrayList<>();
		guests.forEach(guest -> guestResponseDTO.add(converteEntityToDTO(guest)));
		return guestResponseDTO;
	}

	public Guest getGuestById(Long guestId) {

		Optional<Guest> guest = guestRepository.findById(guestId);
		return guest.isPresent() ? guest.get() : new Guest();
	}

}
