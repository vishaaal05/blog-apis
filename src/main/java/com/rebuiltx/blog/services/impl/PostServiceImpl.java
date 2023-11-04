package com.rebuiltx.blog.services.impl;



import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.rebuiltx.blog.entities.Category;
import com.rebuiltx.blog.entities.Post;
import com.rebuiltx.blog.entities.User;
import com.rebuiltx.blog.exceptions.ResourceNotFoundException;
import com.rebuiltx.blog.payloads.PostDto;
import com.rebuiltx.blog.payloads.PostResponse;
import com.rebuiltx.blog.repositories.CategoryRepo;
import com.rebuiltx.blog.repositories.PostRepo;
import com.rebuiltx.blog.repositories.UserRepo;
import com.rebuiltx.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo; 
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo; 
	
	
	
	
	//create
	@Override
	public PostDto createPost(PostDto postDto, int id, int categoryId) {
		
		User user = this.userRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User ", "User Id", id));
		
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category ", "Category Id", categoryId));
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date()); 
		post.setUser(user);
		post.setCategory(category);
		
	    Post newPost = this.postRepo.save(post);
		
		return this.modelMapper.map(newPost, PostDto.class);
	}    

	
	//update
	@Override
	public PostDto updatePost(PostDto postDto, int postId) {
		Post post = this.postRepo.findById(postId)
			    	.orElseThrow(() -> new ResourceNotFoundException("Post ", "Post Id", postId));
	
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post updatedPost = this.postRepo.save(post); 
		
		return this.modelMapper.map(updatedPost, PostDto.class);
	}
	
	
//delete
	@Override
	public void deletePost(int postId) {
	
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));
		this.postRepo.delete(post);

	}
// get single post
	@Override
	public PostDto getPostById( int postId) {
		Post post  = this.postRepo.findById(postId)
		.orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId));
		
		
		return this.modelMapper.map(post, PostDto.class);
	}

	// get all posts
	@Override
	public PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDir) {
		
		
		Sort sort = sortDir.equalsIgnoreCase("asc")?sort= Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
      Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
		
		Page<Post> pagePost = this.postRepo.findAll(pageable); 
		
		List<Post> allPosts = pagePost.getContent();
		
		List<PostDto> postDtos = allPosts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		
		return postResponse;
	}
	
	
	//get all post by category
	@Override
	public PostResponse getPostsByCategory(int categoryId, int pageNumber, int pageSize,String sortBy, String sortDir){
		Category category = this.categoryRepo.findById(categoryId)
				            .orElseThrow(() -> new ResourceNotFoundException("Category ", "Category Id", categoryId));
		
		
		Sort sort = sortDir.equalsIgnoreCase("asc")?sort= Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
//		if (sortDir.equalsIgnoreCase("asc")) {
//			sort= Sort.by(sortBy).ascending();
//			
//		}else {
//			sort = Sort.by(sortBy).descending();
//		}
		
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Post> pagePost = this.postRepo.findByCategory(category, pageable);
		
		List<Post> posts = pagePost.getContent();
		
		 List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		 
			
		PostResponse postResponse = new PostResponse(); 
			
		 postResponse.setContent(postDtos);
		    postResponse.setPageNumber(pagePost.getNumber()); // Set the current page number
		    postResponse.setPageSize(pagePost.getSize()); // Set the page size
		    postResponse.setTotalElements(pagePost.getTotalElements()); // Set the total number of elements
		    
		    postResponse.setTotalPages(pagePost.getTotalPages()); // Set the total number of pages
			postResponse.setLastPage(pagePost.isLast());

		    return postResponse;
	}
	
	//get all posts by user
	@Override 
	public PostResponse getPostsByUser(int id, int pageNumber, int pageSize,String sortBy, String sortDir){
		User user = this.userRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("User", "Id", id));
		
		Sort sort = sortDir.equalsIgnoreCase("asc")?sort= Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		

		Page<Post> pagePost =  this.postRepo.findByUser(user, pageable);
		
		List<Post> posts = pagePost.getContent();
		
		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse(); 
		
		 postResponse.setContent(postDtos);
		    postResponse.setPageNumber(pagePost.getNumber()); // Set the current page number
		    postResponse.setPageSize(pagePost.getSize()); // Set the page size
		    postResponse.setTotalElements(pagePost.getTotalElements()); // Set the total number of elements
		    
		    postResponse.setTotalPages(pagePost.getTotalPages()); // Set the total number of pages
			postResponse.setLastPage(pagePost.isLast());

		
		return postResponse;
	}
	
	//search posts
	@Override
	public List<PostDto> searchPosts(String keyword){
		List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDto = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDto;
	}

}
