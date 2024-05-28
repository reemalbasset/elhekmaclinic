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

import jakarta.servlet.http.HttpSession;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.controllers.UserController;
import com.example.demo.models.User;
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
        // Verify that userRepository.save() method is called once with the user object
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

}



