package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.Model;

import com.example.demo.controllers.RouteController;
import com.example.demo.controllers.UserController;
import com.example.demo.models.Appointment;
import com.example.demo.models.Doctor;
import com.example.demo.models.Nurse;
import com.example.demo.models.User;
import com.example.demo.repositories.AppointmentRepository;
import com.example.demo.repositories.DoctorRepository;
import com.example.demo.repositories.NurseRepository;
import com.example.demo.repositories.UserRepositry;

import jakarta.servlet.http.HttpSession;


public class BookingTest {
    @InjectMocks
    private RouteController RouteController;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
     
    private DoctorRepository doctorRepository;

    @Mock
    private NurseRepository nurseRepository;

    @Mock
    private UserRepositry userRepository;

    @Mock
    private Model model;

    private MockHttpSession session;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        session = new MockHttpSession();
    }

    // @Test
    // public void testBookAppointment_Success() {
    //     Long doctorId = 1L;
    //     Long nurseId = 1L;
    //     String patientName = "John Doe";
    //     java.sql.Date sqlAppointmentDate = java.sql.Date.valueOf(LocalDate.now()); // Assuming you have the appointment date as java.sql.Date
    //     Date appointmentDate = new Date(sqlAppointmentDate.getTime()); // Convert java.sql.Date to java.util.Date
    //     String appointmentTimeStr = "10:00";
    //     String reason = "Regular Checkup";
    //     String username = "testUser";

    //     MockHttpSession session = new MockHttpSession();
    //    session.setAttribute("username", "testUser");

    //     session.setAttribute("username", username);
        
    //     when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(new Doctor()));
    //     when(nurseRepository.findById(nurseId)).thenReturn(Optional.of(new Nurse()));
    //     when(userRepository.findByUsername(username)).thenReturn(new User());

    //     //String view = RouteController.bookAppointment(doctorId, nurseId, patientName, appointmentDate, appointmentTimeStr, reason, session, model);
    //     String view = RouteController.bookAppointment(doctorId, nurseId, patientName, sqlAppointmentDate, appointmentTimeStr, reason, session, model);
    //     assertEquals("redirect:/appointmentreview", view);
    //     verify(appointmentRepository, times(1)).save(any(Appointment.class));
    // }
}
