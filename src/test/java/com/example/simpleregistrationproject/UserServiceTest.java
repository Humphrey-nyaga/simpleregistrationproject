package com.example.simpleregistrationproject;

import com.example.simpleregistrationproject.exception.PasswordMismatchException;
import com.example.simpleregistrationproject.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);

        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("password123");
    }

    @Test
    public void testFindUserByEmail() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        User result = userService.findUserByEmail(user.getEmail());

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getPassword(), result.getPassword());
    }

    @Test
    public void testFindUserByEmailNotFound() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.findUserByEmail(user.getEmail());
        });
    }

    @Test
    public void testCreateUser() {
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.createUser(user);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getPassword(), result.getPassword());
    }

    @Test
    public void testCheckPassword() {
        String password = "password123";
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);

        assertDoesNotThrow(() -> {
            userService.checkPassword(user, password);
        });
    }

    @Test
    public void testCheckPasswordMismatch() {
        String password = "wrongpassword";
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(false);

        assertThrows(PasswordMismatchException.class, () -> {
            userService.checkPassword(user, password);
        });
    }
}

