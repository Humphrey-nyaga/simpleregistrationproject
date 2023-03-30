package com.example.simpleregistrationproject;

import com.example.simpleregistrationproject.exception.PasswordMismatchException;
import com.example.simpleregistrationproject.exception.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
     private final UserRepository userRepository;
     private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



    public User findUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("User with Email: " + " not found."));

    }

    public User createUser(User user){
        return userRepository.save(user);
    }

    public void checkPassword(User user, String password) throws PasswordMismatchException {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new PasswordMismatchException("The provided password is wrong");
        }
    }



}
