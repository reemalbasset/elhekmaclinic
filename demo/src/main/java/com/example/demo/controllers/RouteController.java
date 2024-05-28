package com.example.demo.controllers;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.models.Appointment;
import com.example.demo.models.Doctor;
import com.example.demo.models.Nurse;
import com.example.demo.models.Patient;
import com.example.demo.models.User;
import com.example.demo.repositories.AppointmentRepository;
import com.example.demo.repositories.DoctorRepository;
import com.example.demo.repositories.NurseRepository;
import com.example.demo.repositories.UserRepositry;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class RouteController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private NurseRepository nurseRepository;

    @GetMapping("/User/specialities")
    public ModelAndView specialitiespage() {
        return new ModelAndView("specialities.html");
    }

    @GetMapping("/User/clinicteam")
    public ModelAndView clinicteampage() {
        return new ModelAndView("clinicteam.html");
    }

    @GetMapping("/User/aboutus")
    public ModelAndView aboutuspage() {
        return new ModelAndView("aboutus.html");
    }

    @GetMapping("/User/doctorslist")
    public ModelAndView doctorslistpage() {
        return new ModelAndView("doctorslist.html");
    }

    @GetMapping("/User/doctorsdetails")
    public ModelAndView doctorsdetailspage() {
        return new ModelAndView("doctorsdetails.html");
    }

    @GetMapping("/User/appointmentreview")
    public ModelAndView appointmentreviewpageusr() {
        return new ModelAndView("appointmentreview.html");
    }

    @GetMapping("appointmentreview")
    public ModelAndView appointmentreviewpage() {
        return new ModelAndView("appointmentreview.html");
    }

    @GetMapping("specialities")
    public ModelAndView specialitiespageIndex() {
        return new ModelAndView("specialities.html");
    }

   @GetMapping("booking")
    public ModelAndView bookingpage() {
        return getBookingPageModelAndView(new ModelAndView("booking.html"));
    }

@Autowired
private UserRepositry userRepositry;
    @PostMapping("/booking")
    public String bookAppointment(
            @RequestParam Long doctorId,
            @RequestParam Long nurseId,
            @RequestParam String patientName,
            @RequestParam Date appointmentDate,
            @RequestParam String appointmentTimeStr,
            @RequestParam String reason,
            HttpSession session,
            Model model) {

        try {
            LocalDate currentDate = LocalDate.now();
            LocalTime currentTime = LocalTime.now();
            LocalDate selectedDate = appointmentDate.toLocalDate();
            LocalTime selectedTime;

            try {
                selectedTime = LocalTime.parse(appointmentTimeStr);
            } catch (DateTimeParseException e) {
                model.addAttribute("error", "Invalid time format. Please use HH:mm format.");
                return getBookingPage(model);
            }

            if (selectedDate.isBefore(currentDate) || (selectedDate.isEqual(currentDate) && selectedTime.isBefore(currentTime))) {
                throw new IllegalArgumentException("You cannot book an appointment in the past.");
            }

            LocalTime oneHourAhead = selectedTime.plusHours(1);
            LocalTime oneHourBefore = selectedTime.minusHours(1);

            List<Appointment> appointments = appointmentRepository.findByAppointmentDateAndAppointmentTimeBetween(
                    appointmentDate, Time.valueOf(oneHourBefore), Time.valueOf(oneHourAhead));
            if (!appointments.isEmpty()) {
                throw new IllegalArgumentException("There is already an appointment within an hour of the selected time. Please choose a different time.");
            }

            Optional<Appointment> existingAppointment = appointmentRepository.findByDoctorIdAndAppointmentDateAndAppointmentTime(
                    doctorId, appointmentDate, Time.valueOf(selectedTime));
            if (existingAppointment.isPresent()) {
                throw new IllegalArgumentException("The selected time slot is already booked. Please choose a different time.");
            }
            String username = (String) session.getAttribute("username");
         User user = userRepositry.findByUsername(username);

            Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new IllegalArgumentException("Invalid doctor ID"));
            Nurse nurse = nurseRepository.findById(nurseId).orElseThrow(() -> new IllegalArgumentException("Invalid nurse ID"));

            Appointment appointment = new Appointment();
            appointment.setDoctor(doctor);
            appointment.setNurse(nurse);
            appointment.setPatientName(patientName);
            appointment.setAppointmentDate(appointmentDate);
            appointment.setAppointmentTime(Time.valueOf(selectedTime));
            appointment.setReason(reason);
            appointment.setUsername(username);

            appointmentRepository.save(appointment);
            return "redirect:/appointmentreview";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return getBookingPage(model);
        } catch (Exception e) {
            model.addAttribute("error", "An unexpected error occurred: " + e.getMessage());
            return getBookingPage(model);
        }
    }

    private ModelAndView getBookingPageModelAndView(ModelAndView mav) {
        List<Doctor> doctors = doctorRepository.findAll();
        List<Nurse> nurses = nurseRepository.findAll();
        mav.addObject("doctors", doctors);
        mav.addObject("nurses", nurses);
        return mav;
    }

    private String getBookingPage(Model model) {
        List<Doctor> doctors = doctorRepository.findAll();
        List<Nurse> nurses = nurseRepository.findAll();
        model.addAttribute("doctors", doctors);
        model.addAttribute("nurses", nurses);
        return "booking";
    }
    @GetMapping("/deleteaccount")
    public ModelAndView deleteaccountpageIndex() {
        return new ModelAndView("deleteConfirmation.html");
    }
}
