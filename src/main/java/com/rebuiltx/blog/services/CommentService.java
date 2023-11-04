package com.rebuiltx.blog.services;

import com.rebuiltx.blog.payloads.CommentDto;

public interface CommentService {

	//create
	public CommentDto createComment(CommentDto commentDto, int postId);
	
	//delete
	
	public void deleteComment(int commentId);
}
