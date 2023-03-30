package com.example.simpleregistrationproject;

import com.example.simpleregistrationproject.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testGetUserDataReturnsOkWhenUserExistsAndPasswordMatches() throws Exception {
        // Arrange
        User user = new User("user@example.com", "password");
        when(userService.findUserByEmail(anyString())).thenReturn(user);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        // Act
        ResponseEntity<Map<String, Object>> response = new UserController(passwordEncoder, userService, userRepository).getUserData("user@example.com", "password");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.emptyMap(), response.getBody());
    }

    @Test
    public void shouldReturnNotFoundWhenUserDoesNotExist() throws Exception {
        // Arrange
        when(userService.findUserByEmail(anyString())).thenThrow(new UserNotFoundException("User not found"));
        UserController controller = new UserController(passwordEncoder, userService, userRepository);

        // Act
        ResponseEntity<Map<String, Object>> response = controller.getUserData("user@example.com", "password");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Map<String, Object> expectedBody = new HashMap<>();
        expectedBody.put("", "User not found");
        assertEquals(expectedBody, response.getBody());
    }


    @Test
    public void testGetUserDataReturnsInternalServerErrorWhenUnexpectedExceptionOccurs() throws Exception {
        // Arrange
        when(userService.findUserByEmail(anyString())).thenThrow(new RuntimeException("Unexpected error"));

        // Act
        ResponseEntity<Map<String, Object>> response = new UserController(passwordEncoder, userService, userRepository).getUserData("user@example.com", "password");

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Map<String, Object> expectedBody = new HashMap<>();
        expectedBody.put("", "Internal Server Error");
        assertEquals(expectedBody, response.getBody());
    }

}
