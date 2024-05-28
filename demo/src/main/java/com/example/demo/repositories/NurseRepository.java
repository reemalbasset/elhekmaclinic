package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Nurse;

public interface NurseRepository extends JpaRepository<Nurse,Long>{
        List<Nurse>findAllById(Long id);

}
