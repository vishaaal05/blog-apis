package com.rebuiltx.blog.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.rebuiltx.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

}
