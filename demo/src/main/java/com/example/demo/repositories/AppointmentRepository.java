package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Appointment;
import com.example.demo.models.User;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAll();

    Optional<Appointment> findByDoctorIdAndAppointmentDateAndAppointmentTime(Long doctorId, Date appointmentDate, Time appointmentTime);
    //  User findByUsername(String username);
    List<Appointment> findByAppointmentDateAndAppointmentTimeBetween(Date appointmentDate, Time startTime, Time endTime);
    List<Appointment> findByUsername(String username);
    List<Appointment> findByDoctorUsername(String doctorUsername);
    List<Appointment> findByNurseUsername(String nurseUsername);
}
