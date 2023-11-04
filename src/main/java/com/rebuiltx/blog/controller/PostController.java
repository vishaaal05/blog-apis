package com.rebuiltx.blog.controller;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StreamUtils;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rebuiltx.blog.config.AppConstants;
import com.rebuiltx.blog.payloads.ApiResponse;
import com.rebuiltx.blog.payloads.PostDto;
import com.rebuiltx.blog.payloads.PostResponse;
import com.rebuiltx.blog.services.FileService;
import com.rebuiltx.blog.services.PostService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletResponse;


@RestController
@SecurityRequirement(name = "scheme1")
@RequestMapping("/api")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	
	@Value("${project.image}")
	private String path;
	
	



	//create
	@PostMapping("/user/{id}/category/{categoryId}/post")
	public ResponseEntity<PostDto> createPost(
			@RequestBody PostDto postDto,
			@PathVariable int id,
			@PathVariable int categoryId
			){
		PostDto createdPost = this.postService.createPost(postDto, id, categoryId);
		return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
	}
	
	//get by user
	
	@GetMapping("/user/{id}/posts")
	public ResponseEntity<PostResponse> getPostByUser(
			@PathVariable int id,
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required =false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false)  String sortDir
			
			){
		
		
		PostResponse postResponse = this.postService.getPostsByUser(id, pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}
	
	//get by category
	
	@GetMapping("/category/{categoryId}/posts")
	
	public ResponseEntity<PostResponse> getPostByCategory(
			@PathVariable int categoryId,
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required =false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false)  String sortDir
			){

		
		  PostResponse postResponse = this.postService.getPostsByCategory(categoryId, pageNumber, pageSize, sortBy, sortDir );
		  return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}
	
	// get single post
	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable int postId) {
		 PostDto postDto = this.postService.getPostById(postId);
		 
		 return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
	}
	
	//get all posts
	
	@GetMapping("/posts")
	
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required =false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false)  String sortDir
			){

		PostResponse postResponse =  this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
		
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
		}
	
	//delete
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/post/{postId}")
	public ResponseEntity<ApiResponse> deleteById(@PathVariable int postId) {
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post deleted successfully", true), HttpStatus.OK); 
	}           
	
	
	//update
	
	@PutMapping("/post/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable int postId){
	 PostDto updatedPost	=  this.postService.updatePost(postDto, postId);
	 return ResponseEntity.ok(updatedPost);
	}
	
	//search
	
	@GetMapping("posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(
			@PathVariable("keywords") String keywords
			){
		List<PostDto> result = this.postService.searchPosts(keywords);
		return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
	}
	
	//post image upload 
	 @PostMapping("/post/image/upload/{postId}")
	 public ResponseEntity<PostDto> uploadPostImage(
			 @RequestParam("image") MultipartFile image,
			 @PathVariable int postId
			 ) throws IOException{
			PostDto postDto = this.postService.getPostById(postId);
		String fileName = this.fileService.uploadImage(path, image);
	
		postDto.setImageName(fileName);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK); 
	 }
	 
	 //method to serve files
	 @GetMapping(value = "/post/image/{imageName}" ,produces = MediaType.IMAGE_JPEG_VALUE)
	 public void downloadImage(
			 @PathVariable("imageName") String imageName,
			 HttpServletResponse response
			 ) throws IOException {
		 
		 InputStream resource = this.fileService.getResource(path, imageName);
		 response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		 StreamUtils.copy(resource, response.getOutputStream());
		 
	 }
}
