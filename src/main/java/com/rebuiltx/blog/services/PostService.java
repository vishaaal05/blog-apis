package com.rebuiltx.blog.services;

import java.util.List;


import com.rebuiltx.blog.payloads.PostDto;
import com.rebuiltx.blog.payloads.PostResponse;


public interface PostService {

	//create post
	
	public PostDto createPost(PostDto postDto, int id, int categoryId);
	
	//update post
	
	public PostDto updatePost(PostDto postDto, int postId);
	
	//delete post
	
	public void deletePost(int postId);
	
	//get all post
	
	public PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDir);
	
	//get single post
	
	public PostDto getPostById( int postId);
	
	
	//get all post by category
	
	public PostResponse getPostsByCategory(int categoryId, int pageNumber, int pageSize, String sortBy, String sortDir);
	
	//get all posts by user
	
	public PostResponse getPostsByUser(int id, int pageNumber, int pageSize, String sortBy, String sortDir);
	
	//search posts
	
	public List<PostDto> searchPosts(String keyword);
	
}
