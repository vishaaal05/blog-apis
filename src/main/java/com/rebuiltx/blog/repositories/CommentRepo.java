package com.rebuiltx.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rebuiltx.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
