package com.myhotel.managment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myhotel.managment.domain.Hotel;
import com.myhotel.managment.domain.Offer;
import com.myhotel.managment.dto.response.OfferResponseDTO;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Integer> {

	Offer findByOfferCodeAndHotel(Integer offerCode, Hotel hotel);

	List<Offer> findAllByHotel(Hotel hotel);

	Offer findByOfferCode(Integer offerCode);

	void findDeleteByHotel(Hotel hotel);

	OfferResponseDTO findByHotel(Hotel hotel);
}