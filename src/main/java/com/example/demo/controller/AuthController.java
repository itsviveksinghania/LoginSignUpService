package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.JwtUtil;
import com.example.demo.util.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private SecurityConfig securityConfig;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User userRequest) {

        if (userRequest == null || userRequest.getUsername() == null || userRequest.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new SingUpResponse("Username or password cannot be null"));

        }
        if (userRequest.getUsername().isEmpty() || userRequest.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new SingUpResponse("Username or password cannot be Empty"));
        }

        if (userRepository.findByUsername(userRequest.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new SingUpResponse("Account exists! kindly login to proceed."));
        }

        String hashedPassword = securityConfig.passwordEncoder().encode(userRequest.getPassword());

        User newUser = new User();
        newUser.setUsername(userRequest.getUsername());
        newUser.setPassword(hashedPassword);

        userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.OK).body(new SingUpResponse("User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        if (loginRequest == null || loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new SingUpResponse("Username or password cannot be null"));

        }
        if (loginRequest.getUsername().isEmpty() || loginRequest.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new SingUpResponse("Username or password cannot be Empty"));
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwtToken = jwtUtil.generateToken(userDetails);

            return ResponseEntity.ok(new LoginResponse(jwtToken, "Logged in Successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid credentials");
        }

    }

    @PostMapping("/hello")
    public ResponseEntity<?> hello(@RequestBody HelloRequest helloRequest) {
        if (!jwtUtil.validateToken(helloRequest.getToken())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new HelloResponse("Invalid token"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new HelloResponse("Hello from GreenStitch"));
    }
}

