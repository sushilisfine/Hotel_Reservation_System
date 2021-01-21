package com.myhotel.managment.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myhotel.managment.dto.CategoryDTO;
import com.sun.istack.NotNull;

@RequestMapping("/api/v1/hotels/")
public interface CategoryController {

	@PostMapping("{hotel_id}/categories")
	public ResponseEntity<CategoryDTO> add(@NotNull @PathVariable("hotel_id") Long hotelId,
			@NotNull @RequestBody CategoryDTO category);

	@PutMapping("{hotel_id}/categories/{category_id}")
	public ResponseEntity<CategoryDTO> update(@NotNull @PathVariable("hotel_id") Long hotelId,
			@NotNull @PathVariable("category_id") Long categoryId, @NotNull @RequestBody CategoryDTO category);

	@GetMapping("{hotel_id}/categories")
	public ResponseEntity<List<CategoryDTO>> getAll(@NotNull @PathVariable("hotel_id") Long hotelId);

	@GetMapping("{hotel_id}/categories/{category_id}")
	public ResponseEntity<CategoryDTO> get(@NotNull @PathVariable("hotel_id") Long hotelId,
			@NotNull @PathVariable("category_id") Long categoryId);
}
