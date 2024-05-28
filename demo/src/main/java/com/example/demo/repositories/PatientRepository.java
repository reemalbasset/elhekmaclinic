package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Patient;

public interface PatientRepository extends JpaRepository<Patient,Long>{
        List<Patient>findAllById(Long id);

}
