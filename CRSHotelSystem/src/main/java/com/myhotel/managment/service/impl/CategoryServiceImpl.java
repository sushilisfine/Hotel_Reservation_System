package com.myhotel.managment.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.myhotel.managment.domain.Category;
import com.myhotel.managment.domain.Hotel;
import com.myhotel.managment.dto.CategoryDTO;
import com.myhotel.managment.repository.CategoryRepository;
import com.myhotel.managment.repository.HotelRepository;
import com.myhotel.managment.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	private CategoryRepository categoryRepository;
	private HotelRepository hotelRepository;

	public CategoryServiceImpl(CategoryRepository categoryRepository, HotelRepository hotelRepository) {
		this.categoryRepository = categoryRepository;
		this.hotelRepository = hotelRepository;
	}

	@Override
	public CategoryDTO add(CategoryDTO categoryDTO) {
		Category category = convertDTOToEntity(categoryDTO);
		return convertEntityToDTO(categoryRepository.save(category));
	}

	@Override
	public CategoryDTO update(CategoryDTO categoryDTO) {

		Category category = convertDTOToEntity(categoryDTO);
		categoryRepository.save(category);
		return convertEntityToDTO(category);
	}

	@Override
	public List<CategoryDTO> getAll(Long hotelId) {

		Hotel hotel = Hotel.builder().id(hotelId).build();
		List<Category> categories = categoryRepository.findAllByHotel(hotel);
		return convertEntityToDTO(categories);
	}

	@Override
	public CategoryDTO get(Long categoryId) {
		return convertEntityToDTO(getCategory(categoryId));
	}

	@Override
	public Hotel getHotel(Long hotellId) {

		Optional<Hotel> hotel = hotelRepository.findById(hotellId);
		return hotel.isPresent() ? hotel.get() : new Hotel();
	}

	@Override
	public Category getCategory(Long categoryId) {

		Optional<Category> category = categoryRepository.findById(categoryId);
		return category.isPresent() ? category.get() : new Category();
	}

	private Category convertDTOToEntity(CategoryDTO categoryDTO) {

		return Category.builder().id(categoryDTO.getId()).description(categoryDTO.getDescription())
				.charges(categoryDTO.getCharges()).hotel(Hotel.builder().id(categoryDTO.getHotelId()).build()).build();

	}

	private CategoryDTO convertEntityToDTO(Category category) {

		return CategoryDTO.builder().id(category.getId()).description(category.getDescription())
				.charges(category.getCharges()).hotelId(category.getHotel().getId()).build();

	}

	private List<CategoryDTO> convertEntityToDTO(List<Category> categories) {

		List<CategoryDTO> categoryDTO = new ArrayList<>();
		categories.forEach(category -> categoryDTO.add(convertEntityToDTO(category)));
		return categoryDTO;
	}

}
