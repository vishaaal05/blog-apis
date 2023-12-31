package com.rebuiltx.blog.services.impl;

import java.util.List;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rebuiltx.blog.entities.Category;
import com.rebuiltx.blog.exceptions.ResourceNotFoundException;
import com.rebuiltx.blog.payloads.CategoryDto;
import com.rebuiltx.blog.repositories.CategoryRepo;
import com.rebuiltx.blog.services.CategoryService;


@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		 Category category =  this.modelMapper.map(categoryDto, Category.class);
		 Category addedCategory = this.categoryRepo.save(category);
		return this.modelMapper.map(addedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, int categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
							.orElseThrow(() -> new ResourceNotFoundException("Category ", "Category Id", categoryId));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category updatedCategory = this.categoryRepo.save(category);
		
		return this.modelMapper.map(updatedCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(int categoryId) {
	Category category = this.categoryRepo.findById(categoryId)
		                .orElseThrow(() -> new ResourceNotFoundException("Category ", "Category Id", categoryId));
	this.categoryRepo.delete(category);
	}

	@Override
	public CategoryDto getSingleCategory(int categoryId) {
	  Category category = this.categoryRepo.findById(categoryId)
			  			.orElseThrow(() -> new ResourceNotFoundException("Category ", "Category Id", categoryId));
	  
		return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> categories = this.categoryRepo.findAll();
		List<CategoryDto> categoryDto= categories.stream().map((category) -> this.modelMapper.map(category, CategoryDto.class )).collect(Collectors.toList());
		return  categoryDto;
	}

}
