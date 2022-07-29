package com.example.cdwebbe.controller;

import com.example.cdwebbe.exception.AppException;
import com.example.cdwebbe.model.Role;
import com.example.cdwebbe.model.RoleName;
import com.example.cdwebbe.model.User;
import com.example.cdwebbe.payload.ApiResponse;
import com.example.cdwebbe.payload.JwtAuthenticationResponse;
import com.example.cdwebbe.payload.LoginRequest;
import com.example.cdwebbe.payload.SignUpRequest;
import com.example.cdwebbe.repository.RoleRepository;
import com.example.cdwebbe.repository.UserRepository;
import com.example.cdwebbe.security.JwtTokenProvider;
import com.example.cdwebbe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

import static com.example.cdwebbe.service.impl.UserServiceImpl.sendPlainTextEmail;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) throws MessagingException {
        System.out.print(signUpRequest);
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();
        sendPlainTextEmail("smtp.gmail.com", "587", "tmdt.test1234@gmail.com", "pbpxgmcvuzlydxbw", user.getEmail(), "Register successfully", "Welcome to my website!!!");

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    @PostMapping("/signupadmin")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody SignUpRequest signUpRequest) {
        System.out.print(signUpRequest);
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "Admin registered successfully"));
    }

    @GetMapping("/checkvalidname")
    public ResponseEntity<?> checkValidName(@RequestParam(required = false) String name) {
        if (userRepository.existsByUsername(name)) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.ACCEPTED);
        }
        return new ResponseEntity(new ApiResponse(true, ""), HttpStatus.ACCEPTED);
    }

    @GetMapping("/checkvalidemail")
    public ResponseEntity<?> checkValidEmail(@RequestParam(required = false) String email) {
        if (userRepository.existsByEmail(email)) {
            return new ResponseEntity(new ApiResponse(false, "Email is already taken!"),
                    HttpStatus.ACCEPTED);
        }
        return new ResponseEntity(new ApiResponse(true, ""), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteuser/{id}")
    public ResponseEntity deleteClass(
            @PathVariable long id
    ) {
        String message = userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("Delete user success");
    }


}
