package com.myhotel.managment.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.myhotel.managment.controller.CategoryController;
import com.myhotel.managment.domain.Category;
import com.myhotel.managment.domain.Hotel;
import com.myhotel.managment.dto.CategoryDTO;
import com.myhotel.managment.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CategoryControllerImpl implements CategoryController {

	@Autowired
	private CategoryService categoryService;

	@Override
	public ResponseEntity<CategoryDTO> add(Long hotelId, CategoryDTO category) {

		category.setHotelId(hotelId);

		try {
			if (validateHotel(hotelId))
				return new ResponseEntity<>(categoryService.add(category), HttpStatus.CREATED);
			else
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (RuntimeException e) {
			log.info("Creating new Category failed");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<CategoryDTO> update(Long hotelId, Long categoryId, CategoryDTO category) {

		category.setHotelId(hotelId);
		category.setId(categoryId);

		try {
			if (validateHotelAndCategory(hotelId, categoryId))
				return new ResponseEntity<>(categoryService.update(category), HttpStatus.OK);
			else
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (RuntimeException e) {
			log.info("Updating Category failed");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<List<CategoryDTO>> getAll(Long hotelId) {

		try {
			if (validateHotel(hotelId))
				return new ResponseEntity<>(categoryService.getAll(hotelId), HttpStatus.OK);
			else
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (RuntimeException e) {
			log.info("Getting All Categories failed");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<CategoryDTO> get(Long hotelId, Long categoryId) {

		try {
			if (validateHotelAndCategory(hotelId, categoryId))
				return new ResponseEntity<>(categoryService.get(categoryId), HttpStatus.OK);
			else
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (RuntimeException e) {
			log.info("Getting Category failed");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	private boolean validateHotel(Long hotelId) {

		Hotel hotel = categoryService.getHotel(hotelId);

		if (hotel.getId() == null) {
			log.info("Unable to find hotel with id : {}", hotelId);
			return false;
		}
		return true;
	}

	private boolean validateCategory(Long categoryId) {
		Category category = categoryService.getCategory(categoryId);
		if (category.getId() == null) {
			log.info("Unable to find category with id : {} ", categoryId);
			return false;
		}
		return true;
	}

	private boolean validateHotelAndCategory(Long hotelId, Long categoryId) {
		return (validateHotel(hotelId) && validateCategory(categoryId));
	}
}
