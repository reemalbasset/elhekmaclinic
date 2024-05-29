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
import org.springframework.ui.Model;

import com.example.demo.controllers.RouteController;
import com.example.demo.controllers.UserController;
import com.example.demo.models.Doctor;
import com.example.demo.models.Nurse;
import com.example.demo.models.User;
import com.example.demo.repositories.AppointmentRepository;
import com.example.demo.repositories.DoctorRepository;
import com.example.demo.repositories.NurseRepository;
import com.example.demo.repositories.UserRepositry;

import jakarta.servlet.http.HttpSession;

public class BookingTest {
    @Mock
    private UserRepositry userRepositry;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private NurseRepository nurseRepository;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @InjectMocks
    private RouteController controller;

//     @Test
//    public void testBookAppointment() {
//         // Set up mock objects and data
//         LocalDate futureDate = LocalDate.now().plusDays(1);
//         when(session.getAttribute("username")).thenReturn("testUser");
//         when(userRepositry.findByUsername("testUser")).thenReturn(new User());
//         when(doctorRepository.findById(anyLong())).thenReturn(Optional.of(new Doctor()));
//         when(nurseRepository.findById(anyLong())).thenReturn(Optional.of(new Nurse()));
//         when(appointmentRepository.findByAppointmentDateAndAppointmentTimeBetween(any(), any(), any()))
//             .thenReturn(List.of());
//         when(appointmentRepository.findByDoctorIdAndAppointmentDateAndAppointmentTime(anyLong(), any(), any()))
//             .thenReturn(Optional.empty());

//         // Call the method under test
//         String result = controller.bookAppointment(1L, 2L, "John Doe", Date.valueOf(futureDate),
//                 "10:00", "Checkup Reason", session, model);

//         // Verify the result
//         assertEquals("redirect:/appointmentreview", result);
//         verify(appointmentRepository, times(1)).save(any());
//     }

}
