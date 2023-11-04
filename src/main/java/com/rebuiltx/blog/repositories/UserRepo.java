package com.rebuiltx.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rebuiltx.blog.entities.User;
import com.rebuiltx.blog.payloads.UserDto;




public interface UserRepo extends JpaRepository<User, Integer>{
  Optional<User> findByEmail(String email);
  }
