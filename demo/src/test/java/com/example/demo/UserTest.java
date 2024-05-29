package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.controllers.AdminController;
import com.example.demo.controllers.UserController;
import com.example.demo.models.Doctor;
import com.example.demo.models.Nurse;
import com.example.demo.models.User;
import com.example.demo.repositories.DoctorRepository;
import com.example.demo.repositories.NurseRepository;
import com.example.demo.repositories.PatientRepository;
import com.example.demo.repositories.UserRepositry;


public class UserTest {
    @Test
    public void testAddUser() {
        // Create a mock UserRepository
        UserRepositry userRepository = mock(UserRepositry.class);
        // Create a UserController instance with the mock UserRepository
        UserController userController = new UserController(userRepository);
        
        // Call the method
        ModelAndView mav = userController.addUser();

        // Assert the view name
        assertEquals("registration.html", mav.getViewName());
        // Assert that a user object is added to the ModelAndView
        assertNotNull(mav.getModel().get("user"));
    }

    @Test
    public void testSaveUser() {
        // Create a mock UserRepository
        UserRepositry userRepository = mock(UserRepositry.class);
        // Create a UserController instance with the mock UserRepository
        UserController userController = new UserController(userRepository);
        
        // Create a User object
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");

        // Call the method
        ModelAndView mav = userController.saveUser(user);

        // Assert the view name
        assertEquals("login.html", mav.getViewName());
        // Verify that userRepository.save() method is called once in the mock object with the user object 
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testViewProfile() {
        // Create a mock HttpSession
        HttpSession session = mock(HttpSession.class);

        // Set session attributes
        when(session.getAttribute("username")).thenReturn("testUser");
        when(session.getAttribute("name")).thenReturn("Test Name");
        when(session.getAttribute("dob")).thenReturn("2000-01-01");

        // Create a UserController instance
        UserController userController = new UserController(null); // Pass null as UserRepository is not needed for this test

        // Call the method
        ModelAndView mav = userController.viewprofile(session);

        // Assert the view name
        assertEquals("profile.html", mav.getViewName());

        // Assert model attributes
        assertEquals("testUser", mav.getModel().get("username"));
        assertEquals("Test Name", mav.getModel().get("name"));
        assertEquals("2000-01-01", mav.getModel().get("dob"));
    }
    @Test
public void testUpdateProfile() {
    UserRepositry userRepository = mock(UserRepositry.class);
    HttpSession session = mock(HttpSession.class);
    RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

    when(session.getAttribute("username")).thenReturn("testUser");

    User existingUser = new User();
    existingUser.setUsername("testUser");
    existingUser.setName("Old Name");
    existingUser.setDob("1990-01-01");
    existingUser.setPassword("oldPassword");

    // Set the mock UserRepository to return the existing user when findByUsername is called
    when(userRepository.findByUsername("testUser")).thenReturn(existingUser);

    UserController userController = new UserController(userRepository);

    User updatedUser = new User();
    updatedUser.setUsername("newUser");
    updatedUser.setName("New Name");
    updatedUser.setDob("2000-01-01");
    updatedUser.setPassword("newPassword");

    RedirectView redirectView = userController.updateProfile(updatedUser, session, redirectAttributes);

    assertEquals("/User/Profile", redirectView.getUrl());

    verify(userRepository, times(1)).save(existingUser);

    assertEquals("New Name", existingUser.getName());
    assertEquals("2000-01-01", existingUser.getDob());
    assertEquals("newUser", existingUser.getUsername());

    assertNotNull(existingUser.getPassword());
    assertFalse(existingUser.getPassword().equals("newPassword"));
}

    @Test
    public void testLogin() {
        // Create a mock UserRepository
        UserRepositry userRepository = mock(UserRepositry.class);
        // Create a UserController instance with the mock UserRepository
        UserController userController = new UserController(userRepository);
        
        // Call the method
        ModelAndView mav = userController.login();

        // Assert the view name
        assertEquals("login.html", mav.getViewName());
        // Assert that a user object is added to the ModelAndView
        assertNotNull(mav.getModel().get("user"));
    }
    @Test
    public void testLoginProcessEmptyCredentials() {
        // Create a mock UserRepository
        UserRepositry userRepository = mock(UserRepositry.class);
        // Create a UserController instance with the mock UserRepository
        UserController userController = new UserController(userRepository);

        // Create mocks for HttpSession and RedirectAttributes
        HttpSession session = mock(HttpSession.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Call the method with empty username and password
        RedirectView redirectView = userController.loginProcess("", "", session, redirectAttributes);

        // Assert the redirection view
        assertEquals("Login", redirectView.getUrl());
        // Verify that an error message is added to redirect attributes
        verify(redirectAttributes).addFlashAttribute("errorMessage", "Username and password cannot be empty.");
    }
    @Test
   public void testLoginProcessInvalidUsername() {
        // Create a mock UserRepository
        UserRepositry userRepository = mock(UserRepositry.class);
        // Create a UserController instance with the mock UserRepository
        UserController userController = new UserController(userRepository);

        // Create mocks for HttpSession and RedirectAttributes
        HttpSession session = mock(HttpSession.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Setup mock behavior
        when(userRepository.findByUsername("testUser")).thenReturn(null);

        // Call the method with an invalid username
        RedirectView redirectView = userController.loginProcess("testUser", "testPassword", session, redirectAttributes);

        // Assert the redirection view
        assertEquals("Login", redirectView.getUrl());
        // Verify that an error message is added to redirect attributes
        verify(redirectAttributes).addFlashAttribute("errorMessage", "Invalid username.");
    }
    @Test
    public void testLoginProcessInvalidPassword() {
        // Create a mock UserRepository
        UserRepositry userRepository = mock(UserRepositry.class);
        // Create a UserController instance with the mock UserRepository
        UserController userController = new UserController(userRepository);

        // Create mocks for HttpSession and RedirectAttributes
        HttpSession session = mock(HttpSession.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Setup mock User object
        User dbUser = new User();
        dbUser.setUsername("testUser");
        dbUser.setPassword(org.mindrot.jbcrypt.BCrypt.hashpw("correctPassword", org.mindrot.jbcrypt.BCrypt.gensalt()));
        when(userRepository.findByUsername("testUser")).thenReturn(dbUser);

        // Call the method with an invalid password
        RedirectView redirectView = userController.loginProcess("testUser", "wrongPassword", session, redirectAttributes);

        // Assert the redirection view
        assertEquals("Login", redirectView.getUrl());
        // Verify that an error message is added to redirect attributes
        verify(redirectAttributes).addFlashAttribute("errorMessage", "Invalid password.");
    }
    @Test
    public void testLoginProcessValidCredentialsAdmin() {
        // Create a mock UserRepository
        UserRepositry userRepository = mock(UserRepositry.class);
        // Create a UserController instance with the mock UserRepository
        UserController userController = new UserController(userRepository);

        // Create mocks for HttpSession and RedirectAttributes
        HttpSession session = mock(HttpSession.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Setup mock User object
        User dbUser = new User();
        dbUser.setUsername("testUser");
        dbUser.setPassword(org.mindrot.jbcrypt.BCrypt.hashpw("correctPassword", org.mindrot.jbcrypt.BCrypt.gensalt()));
        dbUser.setRole("admin");
        when(userRepository.findByUsername("testUser")).thenReturn(dbUser);

        // Call the method with valid credentials
        RedirectView redirectView = userController.loginProcess("testUser", "correctPassword", session, redirectAttributes);

        // Assert the redirection view
        assertEquals("/Admin/Dashboard", redirectView.getUrl());
        // Verify that session attributes are set
        verify(session).setAttribute("username", "testUser");
        verify(session).setAttribute("type", "admin");
    }
    @Test
    public void testLoginProcessValidCredentialsUser() {
        // Create a mock UserRepository
        UserRepositry userRepository = mock(UserRepositry.class);
        // Create a UserController instance with the mock UserRepository
        UserController userController = new UserController(userRepository);

        // Create mocks for HttpSession and RedirectAttributes
        HttpSession session = mock(HttpSession.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Setup mock User object
        User dbUser = new User();
        dbUser.setUsername("testUser");
        dbUser.setPassword(org.mindrot.jbcrypt.BCrypt.hashpw("correctPassword", org.mindrot.jbcrypt.BCrypt.gensalt()));
        dbUser.setRole("user");
        when(userRepository.findByUsername("testUser")).thenReturn(dbUser);

        // Call the method with valid credentials
        RedirectView redirectView = userController.loginProcess("testUser", "correctPassword", session, redirectAttributes);

        // Assert the redirection view
        assertEquals("Profile", redirectView.getUrl());
        // Verify that session attributes are set
        verify(session).setAttribute("username", "testUser");
        verify(session).setAttribute("type", "user");
    }
    @Test
    public void testLogout() {
        // Create a mock HttpSession
        HttpSession session = mock(HttpSession.class);
        // Create a UserController instance
        UserController userController = new UserController(null);

        // Call the logout method
        RedirectView redirectView = userController.logout(session);

        // Verify that session.invalidate() is called
        verify(session).invalidate();
        // Assert the redirection view
        assertEquals("/User/Login", redirectView.getUrl());
        // Assert that the redirection is context relative
        assertTrue(redirectView.getUrl().startsWith("/"));
    }
    @Test
   public void testDeleteUserConfirm() {
        // Create a mock HttpSession
        HttpSession session = mock(HttpSession.class);
        // Set up mock behavior for session
        when(session.getAttribute("username")).thenReturn("testUser");
        
        // Create a UserController instance
        UserRepositry userRepositry = mock(UserRepositry.class);
        UserController userController = new UserController(userRepositry);

        // Call the deleteUserConfirm method
        ModelAndView mav = userController.deleteUserConfirm(session);

        // Assert the view name
        assertEquals("deleteConfirmation.html", mav.getViewName());
        // Assert that the username is added to the ModelAndView
        assertEquals("testUser", mav.getModel().get("username"));
    }
    @Test
    public void testDeleteUserSuccess() {
        // Create mock objects
        UserRepositry userRepositry = mock(UserRepositry.class);
        HttpSession session = mock(HttpSession.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Create a UserController instance with the mock UserRepository
        UserController userController = new UserController(userRepositry);

        // Set up mock behavior
        when(session.getAttribute("username")).thenReturn("testUser");
        User user = new User();
        user.setUsername("testUser");
        when(userRepositry.findByUsername("testUser")).thenReturn(user);

        // Call the deleteUser method
        RedirectView redirectView = userController.deleteUser(session, redirectAttributes);

        // Verify interactions
        verify(userRepositry, times(1)).delete(user);
        verify(session, times(1)).invalidate();
        verify(redirectAttributes).addFlashAttribute("message", "User account deleted successfully.");

        // Assert the redirection view
        assertEquals("/User/Login", redirectView.getUrl());
    }

    @Test
    public void testDeleteUserNotFound() {
        // Create mock objects
        UserRepositry userRepositry = mock(UserRepositry.class);
        HttpSession session = mock(HttpSession.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Create a UserController instance with the mock UserRepository
        UserController userController = new UserController(userRepositry);

        // Set up mock behavior
        when(session.getAttribute("username")).thenReturn("testUser");
        when(userRepositry.findByUsername("testUser")).thenReturn(null);

        // Call the deleteUser method
        RedirectView redirectView = userController.deleteUser(session, redirectAttributes);

        // Verify interactions
        verify(userRepositry, never()).delete(any());
        verify(session, never()).invalidate();
        verify(redirectAttributes).addFlashAttribute("errorMessage", "User not found. Please try again.");

        // Assert the redirection view
        assertEquals("/User/Delete", redirectView.getUrl());
    }
    @Test
    public void testDeleteDoctorSuccess() {
    // Create mock objects
    DoctorRepository doctorRepository = mock(DoctorRepository.class);
    RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

    // Create a DoctorController instance with the mock DoctorRepository
    AdminController doctorController = new AdminController();

    // Mock behavior
    Long doctorId = 1L;
    Doctor doctor = new Doctor();
    doctor.setId(doctorId);
    Optional<Doctor> doctorOptional = Optional.of(doctor);
    when(doctorRepository.findById(doctorId)).thenReturn(doctorOptional);

    // Call the deleteDoctor method
    RedirectView redirectView = doctorController.deleteDoctor(doctorId, redirectAttributes);

    // Verify interactions
    verify(doctorRepository, times(1)).deleteById(doctorId);
    verify(redirectAttributes).addFlashAttribute("successMessage", "Doctor deleted successfully.");

    // Assert the redirection view
    assertEquals("/Admin/view_doctors", redirectView.getUrl());
}

    @Test
    public void testDeleteDoctorNotFound() {
    // Create mock objects
    DoctorRepository doctorRepository = mock(DoctorRepository.class);
    RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

    // Create a DoctorController instance with the mock DoctorRepository
    AdminController doctorController = new AdminController();

    // Mock behavior
    Long doctorId = 1L;
    Optional<Doctor> doctorOptional = Optional.empty();
    when(doctorRepository.findById(doctorId)).thenReturn(doctorOptional);

    // Call the deleteDoctor method
    RedirectView redirectView = doctorController.deleteDoctor(doctorId, redirectAttributes);

    // Verify interactions
    verify(doctorRepository, never()).deleteById(any());
    verify(redirectAttributes).addFlashAttribute("errorMessage", "Doctor not found.");

    // Assert the redirection view
    assertEquals("/Admin/view_doctors", redirectView.getUrl());
}

    @Test
    public void testDeleteDoctorException() {
    // Create mock objects
    DoctorRepository doctorRepository = mock(DoctorRepository.class);
    RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

    // Create a DoctorController instance with the mock DoctorRepository
    AdminController doctorController = new AdminController();

    // Mock behavior
    Long doctorId = 1L;
    when(doctorRepository.findById(doctorId)).thenThrow(new RuntimeException("Test Exception"));

    // Call the deleteDoctor method
    RedirectView redirectView = doctorController.deleteDoctor(doctorId, redirectAttributes);

    // Verify interactions
    verify(doctorRepository, never()).deleteById(any());
    verify(redirectAttributes).addFlashAttribute("errorMessage", "An error occurred while deleting the doctor.");

    // Assert the redirection view
    assertEquals("/Admin/view_doctors", redirectView.getUrl());
}

    @Autowired
    private NurseRepository nurseRepository;

    @Autowired
    private PatientRepository patientRepository;

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
            String encoddedPassword = org.mindrot.jbcrypt.BCrypt.hashpw(nurse.getPassword(), org.mindrot.jbcrypt.BCrypt.gensalt(12));
            nurse.setPassword(encoddedPassword);
            nurseRepository.save(nurse);
            redirectAttributes.addFlashAttribute("successMessage", "Nurse added successfully.");
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to save nurse details due to a constraint violation.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to save nurse details.");
        }
        // Redirect to the same page
        mav.setViewName("redirect:/Admin/addNurse");
        return mav;
    }

    @GetMapping("viewNurse")
    public ModelAndView getViewNurse() {
        ModelAndView mav = new ModelAndView("viewNurse");
        List<Nurse> nurses = this.nurseRepository.findAll();
        mav.addObject("nurses", nurses);
        return mav;
    }

    @PostMapping("deleteNurse")
    public RedirectView deleteNurse(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            // Check if the nurse exists
            Optional<Nurse> nurseOptional = nurseRepository.findById(id);
            if (nurseOptional.isPresent()) {
                // Delete the nurse from the database
                nurseRepository.deleteById(id);
                redirectAttributes.addFlashAttribute("successMessage", "Nurse deleted successfully.");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Nurse not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while deleting the nurse.");
        }

        // Redirect back to the viewNurse page after deletion
        return new RedirectView("/Admin/viewNurse", true);
    }
     @InjectMocks
    private AdminController adminController;

    

    @Test
    public void testDeleteNurseSuccess() {
        // Mock objects and behavior
        Long nurseId = 1L;
        Nurse nurse = new Nurse();
        nurse.setId(nurseId);
        Optional<Nurse> nurseOptional = Optional.of(nurse);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        when(nurseRepository.findById(nurseId)).thenReturn(nurseOptional);

        // Call the deleteNurse method
        RedirectView redirectView = adminController.deleteNurse(nurseId, redirectAttributes);

        // Verify interactions
        verify(nurseRepository).deleteById(nurseId);
        verify(redirectAttributes).addFlashAttribute("successMessage", "Nurse deleted successfully.");

        // Assert the redirection view
        assertEquals("/Admin/viewNurse", redirectView.getUrl());
    }

    @Test
    public void testGetViewNurse() {
        // Mock data
        List<Nurse> mockNurses = new ArrayList<>();
        Nurse nurse1 = new Nurse();
        nurse1.setId(1L);
        nurse1.setName("Nurse 1");
        mockNurses.add(nurse1);

        // Mock behavior of nurseRepository
        when(nurseRepository.findAll()).thenReturn(mockNurses);

        // Call the method
        ModelAndView mav = adminController.getNurses();

        // Assertions
        assertEquals("viewNurse", mav.getViewName());
        List<Nurse> nurses = (List<Nurse>) mav.getModel().get("nurses");
        assertEquals(1, nurses.size());
        assertEquals("Nurse 1", nurses.get(0).getName());
    }
}



