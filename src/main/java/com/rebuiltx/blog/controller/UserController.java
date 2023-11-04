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
import com.rebuiltx.blog.payloads.UserDto;
import com.rebuiltx.blog.services.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@SecurityRequirement(name = "scheme1")
@RequestMapping("/api/")
public class UserController {
	
	@Autowired
	private UserService userService;
	


	//Post - create user
	@PostMapping("user")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
	   UserDto createUserDto  = this.userService.createUser(userDto);
	return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
		
	}
	
	//Put - update user
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("user/{id}")
	public ResponseEntity<UserDto> updateUser(@Valid @PathVariable int id, @RequestBody UserDto userDto ){
		UserDto updatedUser =  this.userService.updateUser(userDto, id);
		return ResponseEntity.ok(updatedUser);
	}
	
	
	//Delete - delete user
	
	@DeleteMapping("user/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable int id){
		this.userService.deleteUser(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted successfully", true), HttpStatus.OK);
		
	}
	
	
	// get - user get
	
	@GetMapping("users")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		return ResponseEntity.ok(this.userService.getAllUser());
	}

	@GetMapping("user/{id}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable int id){
	return ResponseEntity.ok(this.userService.getUserById(id));
	}
}
