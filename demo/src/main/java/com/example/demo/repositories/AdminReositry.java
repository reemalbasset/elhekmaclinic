package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.User;

public interface AdminReositry extends JpaRepository<User,Integer>  {
    User findByUsername(String username);
}
