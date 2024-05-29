package com.example.demo.repositories;


import com.example.demo.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor>findAllById(Long id);
    Doctor findByUsername(String username);
}