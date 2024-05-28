package com.example.demo.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.models.Appointment;
import com.example.demo.models.Doctor;
import com.example.demo.models.Nurse;
import com.example.demo.models.Patient;
import com.example.demo.models.User;
import com.example.demo.repositories.DoctorRepository;
import com.example.demo.repositories.NurseRepository;
import com.example.demo.repositories.PatientRepository;
import com.example.demo.repositories.UserRepositry;
import com.example.demo.repositories.AppointmentRepository;



import org.springframework.ui.Model;
import java.nio.file.Path;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/Admin")
public class AdminController {
    @Autowired
    private UserRepositry AdminRepositry;
    private UserRepositry UserRepositry;
    @Autowired
    private AppointmentRepository appreo;

    @GetMapping("Index")
   public ModelAndView index() {
       ModelAndView mav = new ModelAndView("index.html");
       return mav;
   }


    @GetMapping("Profile")
    public ModelAndView viewprofile(HttpSession session) {
        ModelAndView mav = new ModelAndView("profile.html");
        mav.addObject("username", (String) session.getAttribute("username"));
        mav.addObject("role", (String) session.getAttribute("role"));
        return mav;
    }

    @GetMapping("EditProfile")
      public ModelAndView editProfile(HttpSession session) {
         ModelAndView mav = new ModelAndView("editProfile.html");
         String username = (String) session.getAttribute("username");
         User user = UserRepositry.findByUsername(username);
         mav.addObject("user", user);
         return mav;
      }
      @PostMapping("EditProfile")
      public RedirectView updateProfile(@ModelAttribute User updatedUser, HttpSession session, RedirectAttributes redirectAttributes) {
          // Retrieve the existing user from the session
          String username = (String) session.getAttribute("username");
          User existingUser = UserRepositry.findByUsername(username);
          
          // Update the existing user with the values from the updated user
          if (existingUser != null) {
              try {
                  existingUser.setName(updatedUser.getName());
                  existingUser.setDob(updatedUser.getDob());
                  existingUser.setUsername(updatedUser.getUsername());
                  
                  // Check if the password field is provided in the form
                  if (!updatedUser.getPassword().isEmpty()) {
                      // Hash and update the password only if provided
                      existingUser.setPassword(BCrypt.hashpw(updatedUser.getPassword(), BCrypt.gensalt(12)));
                  }
                  
                  // Save the updated user
                  UserRepositry.save(existingUser);
                  
                  // Redirect to profile page after successful update
                  return new RedirectView("/Admin/Profile", true);
              } catch (Exception e) {
                  // Log the exception
                  e.printStackTrace();
                  // Add error message to redirect attributes
                  redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while updating your profile.");
              }
          } else {
              // User not found in the session
              redirectAttributes.addFlashAttribute("errorMessage", "User not found. Please try again.");
          }
          
          // Redirect back to the edit profile page in case of errors
          return new RedirectView("/Admin/EditProfile", true);
      }

    

    @GetMapping("Dashboard")
    public ModelAndView viewdashboard(HttpSession session) {
        ModelAndView mav = new ModelAndView("dashboard.html");
        mav.addObject("username", (String) session.getAttribute("username"));
        return mav;
    }

    @GetMapping("Patient")
    public ModelAndView getUser(){
        ModelAndView mav = new ModelAndView("patients.html");
        List<User> users=this.UserRepositry.findAll();
        mav.addObject( "user", users);
        return mav;
    }

    @GetMapping("viewApp")
    public ModelAndView getApp(){
        ModelAndView mav = new ModelAndView("viewApp.html");
        List<Appointment> appointments=this.appreo.findAll();
        mav.addObject( "appointments", appointments);
        return mav;
    }
    @PostMapping("deleteAppointment")
    public RedirectView deleteAppointment(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            // Check if the doctor exists
            Optional<Appointment> appointmentOptional = appreo.findById(id);
            if (appointmentOptional.isPresent()) {
                // Delete the doctor from the database
                appreo.deleteById(id);
                redirectAttributes.addFlashAttribute("successMessage", "Appointment deleted successfully.");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Appointment not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while deleting appointment.");
        }
        
        // Redirect back to the view_doctors page after deletion
        return new RedirectView("/Admin/Dashboard", true);
    }
    

    @GetMapping("specialities")
    public ModelAndView specialitiespage() {
        ModelAndView mav = new ModelAndView("specialities.html");
        return mav;
    }
    @GetMapping("clinicteam")
    public ModelAndView clinicteampage() {
        ModelAndView mav = new ModelAndView("clinicteam.html");
        return mav;
    }
    @GetMapping("aboutus")
    public ModelAndView aboutuspage() {
        ModelAndView mav = new ModelAndView("aboutus.html");
        return mav;
    }
    @GetMapping("doctorslist")
    public ModelAndView doctorslistpage() {
        ModelAndView mav = new ModelAndView("doctorslist.html");
        return mav;
    }
    @GetMapping("doctorsdetails")
    public ModelAndView doctorsdetailspage() {
        ModelAndView mav = new ModelAndView("doctorsdetails.html");
        return mav;
    }
    @GetMapping("booking")
    public ModelAndView bookingpage() {
        ModelAndView mav = new ModelAndView("booking.html");
        return mav;
    }
    @GetMapping("appointmentreview")
    public ModelAndView appointmentreviewpage() {
        ModelAndView mav = new ModelAndView("appointmentreview.html");
        return mav;
    }








    @Autowired
    private DoctorRepository doctorRepository;

    // Display form to add doctor
    @GetMapping("addDoctor")
    public ModelAndView showAddDoctorForm() {
        ModelAndView mav = new ModelAndView("add_doctor");
        mav.addObject("doctor", new Doctor());
        return mav;
    }
    
    //a
    @PostMapping("/addDoctor")
    public ModelAndView saveDoctor(@ModelAttribute("doctor") Doctor doctor,
                                   RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
        try {
            // Save doctor details
            String encoddedPassword=org.mindrot.jbcrypt.BCrypt.hashpw(doctor.getPassword(),org.mindrot.jbcrypt.BCrypt.gensalt(12));
      doctor.setPassword(encoddedPassword);
            doctorRepository.save(doctor);
            redirectAttributes.addFlashAttribute("successMessage", "Doctor added successfully.");
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to save doctor details due to a constraint violation.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to save doctor details.");
        }
        // Redirect to the same page
        mav.setViewName("redirect:/Admin/addDoctor");
        return mav;
    }
 

// @GetMapping("updateDoctor")
// public ModelAndView editDoctor(@RequestParam Long id) {
//     ModelAndView mav = new ModelAndView("updateDoctor");
//     Doctor doctor = doctorRepository.findById(id).orElse(null);

//     if (doctor == null) {
//         // If doctor is not found, add an error message and redirect
//         mav.addObject("errorMessage", "Doctor not found");
//         mav.setViewName("redirect:/Admin/view_doctors");
//     } else {
//         // If doctor is found, add the doctor object to the view
//         mav.addObject("doctor", doctor);
//     }

//     return mav;
// }

// @PostMapping("updateDoctor")
// public RedirectView updateDoctor(@ModelAttribute Doctor updatedDoctor, RedirectAttributes redirectAttributes) {
//     Doctor existingDoctor = doctorRepository.findById(updatedDoctor.getId()).orElse(null);

//     if (existingDoctor != null) {
//         try {
//             // Update doctor details
//             existingDoctor.setName(updatedDoctor.getName());
//             existingDoctor.setEmail(updatedDoctor.getEmail());
//             existingDoctor.setSpecialty(updatedDoctor.getSpecialty());
//             existingDoctor.setUniversityName(updatedDoctor.getUniversityName());

//             // Handle password update
//             if (!updatedDoctor.getPassword().isEmpty()) {
//                 // Hash the new password
//                 existingDoctor.setPassword(BCrypt.hashpw(updatedDoctor.getPassword(), BCrypt.gensalt(12)));
//             }

//             // Save the updated doctor information
//             doctorRepository.save(existingDoctor);

//             // Add a success message
//             redirectAttributes.addFlashAttribute("successMessage", "Doctor updated successfully.");
//             // Redirect to the list of doctors
//             return new RedirectView("/Admin/view_doctors", true);
//         } catch (Exception e) {
//             // Log the exception for debugging
//             e.printStackTrace();
//             // Add an error message and redirect back to the edit form
//             redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while updating the doctor.");
//             return new RedirectView("/Admin/updateDoctor?id=" + updatedDoctor.getId(), true);
//         }
//     } else {
//         // Doctor not found, redirect to the list of doctors
//         redirectAttributes.addFlashAttribute("errorMessage", "Doctor not found.");
//         return new RedirectView("/Admin/view_doctors", true);
//     }
// }







@GetMapping("view_doctors")
public ModelAndView getdoctor() {
ModelAndView mav = new ModelAndView("view_doctors.html");
List<Doctor> doctor = this.doctorRepository.findAll();
mav.addObject("doctors", doctor);
return mav;
}
@PostMapping("deleteDoctor")
public RedirectView deleteDoctor(@RequestParam Long id, RedirectAttributes redirectAttributes) {
    try {
        // Check if the doctor exists
        Optional<Doctor> doctorOptional = doctorRepository.findById(id);
        if (doctorOptional.isPresent()) {
            // Delete the doctor from the database
            doctorRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Doctor deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Doctor not found.");
        }
    } catch (Exception e) {
        e.printStackTrace();
        redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while deleting the doctor.");
    }
    
    // Redirect back to the view_doctors page after deletion
    return new RedirectView("/Admin/view_doctors", true);
}
//delete nurse
@PostMapping("deleteNurse")
public RedirectView deleteNurse(@RequestParam Long id, RedirectAttributes redirectAttributes) {
    try {
        // Check if the doctor exists
        Optional<Nurse> nurseOptional = nurserepository.findById(id);
        if (nurseOptional.isPresent()) {
            // Delete the doctor from the database
            nurserepository.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "nurse deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "nurse not found.");
        }
    } catch (Exception e) {
        e.printStackTrace();
        redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while deleting the nurse.");
    }
    
    // Redirect back to the view_doctors page after deletion
    return new RedirectView("/Admin/viewNurse", true);
}
//delete patient
@PostMapping("deletePatient")
public RedirectView deletePatient(@RequestParam Long id, RedirectAttributes redirectAttributes) {
    try {
        // Check if the doctor exists
        Optional<Patient> patientOptional = patientRepositry.findById(id);
        if (patientOptional.isPresent()) {
            // Delete the doctor from the database
            patientRepositry.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "patient deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "patient not found.");
        }
    } catch (Exception e) {
        e.printStackTrace();
        redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while deleting the patient.");
    }
    
    // Redirect back to the view_doctors page after deletion
    return new RedirectView("/Admin/viewPatient", true);
}

@Autowired
private NurseRepository nurserepository;

@GetMapping("addNurse")
public ModelAndView showNurseForm() {
    ModelAndView mav = new ModelAndView("add_nurse");
    mav.addObject("nurse", new Nurse());
    return mav;
}

@PostMapping("addNurse")
public ModelAndView saveNurse(@ModelAttribute Nurse nurse,
                               RedirectAttributes redirectAttributes) {
    ModelAndView mav = new ModelAndView();
    try {
        // Save Nurse details
        String encoddedPassword=org.mindrot.jbcrypt.BCrypt.hashpw(nurse.getPassword(),org.mindrot.jbcrypt.BCrypt.gensalt(12));
      nurse.setPassword(encoddedPassword);
        nurserepository.save(nurse);
        redirectAttributes.addFlashAttribute("successMessage", "Doctor added successfully.");
    } catch (DataIntegrityViolationException e) {
        e.printStackTrace();
        redirectAttributes.addFlashAttribute("errorMessage", "Failed to save doctor details due to a constraint violation.");
    } catch (Exception e) {
        e.printStackTrace();
        redirectAttributes.addFlashAttribute("errorMessage", "Failed to save doctor details.");
    }
    // Redirect to the same page
    mav.setViewName("redirect:/Admin/addNurse");
    return mav;
}
@GetMapping("viewNurse")
public ModelAndView getNurses() {
ModelAndView mav = new ModelAndView("viewNurse");
List<Nurse> nurses = this.nurserepository.findAll();
mav.addObject("Nurses", nurses);
return mav;
}

@Autowired
private PatientRepository patientRepositry ;
@GetMapping("addPatient")
public ModelAndView showPatientForm() {
    ModelAndView mav = new ModelAndView("add_patient");
    mav.addObject("patient", new Patient());
    return mav;
}

@PostMapping("addPatient")
public ModelAndView savePatient(@ModelAttribute Patient patient,
                               RedirectAttributes redirectAttributes) {
    ModelAndView mav = new ModelAndView();
    try {
        // Save patient details
        String encoddedPassword=org.mindrot.jbcrypt.BCrypt.hashpw(patient.getPassword(),org.mindrot.jbcrypt.BCrypt.gensalt(12));
      patient.setPassword(encoddedPassword);
        patientRepositry.save(patient);
        redirectAttributes.addFlashAttribute("successMessage", "Patient added successfully.");
    } catch (DataIntegrityViolationException e) {
        e.printStackTrace();
        redirectAttributes.addFlashAttribute("errorMessage", "Failed to save patient details due to a constraint violation.");
    } catch (Exception e) {
        e.printStackTrace();
        redirectAttributes.addFlashAttribute("errorMessage", "Failed to save patient details.");
    }
    // Redirect to the same page
    mav.setViewName("redirect:/Admin/addPatient");
    return mav;
}
@GetMapping("viewPatient")
public ModelAndView getpatient() {
ModelAndView mav = new ModelAndView("viewPatient");
List<Patient> patients = this.patientRepositry.findAll();
mav.addObject("patients", patients);
return mav;
}
}
