package com.rebuiltx.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rebuiltx.blog.entities.Comment;
import com.rebuiltx.blog.entities.Post;
import com.rebuiltx.blog.exceptions.ResourceNotFoundException;
import com.rebuiltx.blog.payloads.CommentDto;
import com.rebuiltx.blog.repositories.CommentRepo;
import com.rebuiltx.blog.repositories.PostRepo;
import com.rebuiltx.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepo commentRepo;

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, int postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post ", "Post Id", postId));
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment savedComment = this.commentRepo.save(comment);
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(int commentId) {
		Comment comment = this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment ", "Comment Id", commentId));
		this.commentRepo.delete(comment);
	}

}
