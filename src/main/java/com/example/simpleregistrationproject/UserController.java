package com.example.simpleregistrationproject;


import com.example.simpleregistrationproject.exception.PasswordMismatchException;
import com.example.simpleregistrationproject.exception.UserExistsException;
import com.example.simpleregistrationproject.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/")
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserRepository userRepository;


    public UserController(PasswordEncoder passwordEncoder,UserService userService, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.userRepository = userRepository;
    }


    @GetMapping("/getData")
    public ResponseEntity<Map<String, Object>> getUserData(@RequestParam String email, @RequestParam String password) {
        try {
            /*Check given password's hash matches the existing hash in the database*/
            User currentUser = userService.findUserByEmail(email);
            userService.checkPassword(currentUser, password);
            Map<String, Object> emptyMapResponse = new HashMap<>();
            return ResponseEntity.ok(emptyMapResponse);
        } catch (UserNotFoundException e) {
            Map<String, Object> err = new HashMap<>();
            err.put("", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
        }catch (PasswordMismatchException e) {
            Map<String, Object> err = new HashMap<>();
            err.put("", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
        }
        catch (Exception e) {
            Map<String, Object> err = new HashMap<>();
            err.put("", "Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
        }
    }

    @PostMapping("/postData")
    public Map<String, Object> postUserData(@RequestBody User user) {
        try {
            Optional<User> newUser = userRepository.findByEmail(user.getEmail());
            if (newUser.isPresent()) {
                throw new UserExistsException("The email provided " + user.getEmail()+ " already exists");
            }
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            userService.createUser(user);
            return Collections.emptyMap();
        } catch (UserExistsException e) {
            throw e;
        }
    }



}

