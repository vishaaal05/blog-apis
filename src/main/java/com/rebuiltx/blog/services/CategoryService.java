package com.rebuiltx.blog.services;

import java.util.List;

import com.rebuiltx.blog.payloads.CategoryDto;


public interface CategoryService {

	//create
	public CategoryDto createCategory(CategoryDto categoryDto);

	//update
	public CategoryDto updateCategory(CategoryDto categoryDto, int CategoryId);
	
	//delete
	public void deleteCategory(int categoryId);
	
	//get
	public CategoryDto getSingleCategory(int categoryId);
	
	//get All
	public List<CategoryDto> getAllCategory();
}
