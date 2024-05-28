package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Appointment;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAll();

    Optional<Appointment> findByDoctorIdAndAppointmentDateAndAppointmentTime(Long doctorId, Date appointmentDate, Time appointmentTime);

    List<Appointment> findByAppointmentDateAndAppointmentTimeBetween(Date appointmentDate, Time startTime, Time endTime);
}
