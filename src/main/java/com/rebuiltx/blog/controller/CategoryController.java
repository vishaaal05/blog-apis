package com.rebuiltx.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rebuiltx.blog.payloads.ApiResponse;
import com.rebuiltx.blog.payloads.CategoryDto;
import com.rebuiltx.blog.services.CategoryService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@SecurityRequirement(name = "scheme1")
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	//post - create
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
	 CategoryDto createdCategory = this.categoryService.createCategory(categoryDto);
	return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
		
	}
	
	//Put - update category
	@PreAuthorize("hasRole('ADMIN')")
		@PutMapping("/{categoryId}")
		public ResponseEntity<CategoryDto> updateCategory(@Valid @PathVariable int categoryId, @RequestBody CategoryDto categoryDto){
		CategoryDto updatedCategory =  this.categoryService.updateCategory(categoryDto, categoryId);
			return new ResponseEntity<CategoryDto>(updatedCategory, HttpStatus.OK);
		}
	
		
		//Delete - delete category
	@PreAuthorize("hasRole('ADMIN')")
		@DeleteMapping("/{categoryId}")
		public ResponseEntity<ApiResponse> deleteUser(@PathVariable int categoryId){
			this.categoryService.deleteCategory(categoryId);
			return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted successfully", true), HttpStatus.OK);
			
		}
		
		
		// get - all category get
		
		@GetMapping("/")
		public ResponseEntity<List<CategoryDto>> getAllCategories(){
	    List<CategoryDto> categories = this.categoryService.getAllCategory();
	    return ResponseEntity.ok(categories);
		}

		//get -- single category
		@GetMapping("/{categoryId}")
		public ResponseEntity<CategoryDto> getCategoryByID(@PathVariable int categoryId){
		 CategoryDto categoryDto	= this.categoryService.getSingleCategory(categoryId);
		return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);
		}
	
}
