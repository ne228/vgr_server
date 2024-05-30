package com.example.ais_ecc.controllers;

import com.example.ais_ecc.JwtTokenUtil;
import com.example.ais_ecc.entity.Role;
import com.example.ais_ecc.entity.User;
import com.example.ais_ecc.payload.request.AnonLoginRequest;
import com.example.ais_ecc.payload.request.LoginRequest;
import com.example.ais_ecc.payload.request.SignupRequest;
import com.example.ais_ecc.payload.response.JwtResponse;
import com.example.ais_ecc.payload.response.MessageResponse;
import com.example.ais_ecc.repositories.RoleRepository;
import com.example.ais_ecc.repositories.UserRepository;
import com.example.ais_ecc.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

//@CrossOrigin(origins = "*", maxAge = 3600)
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
    PasswordEncoder encoder;

    @Autowired
//  JwtUtils jwtUtils;

    JwtTokenUtil jwtUtils;


    @GetMapping("/whoami")
    public ResponseEntity<?> whoAmi() {

//    Authentication authentication = authenticationManager.authenticate(
//            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
//    String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(new MessageResponse("ok " + userDetails.getUsername()));
    }
    private String genRandomString(int size) {
        SecureRandom random = new SecureRandom();
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }


    @PostMapping("/guest_signin")
    public ResponseEntity<?> authenticateAnonUser(@Valid @RequestBody AnonLoginRequest anonLoginRequest) {

        var password = "password123";
        var username = anonLoginRequest.getUsername();
        username += "_" + genRandomString(5);


        while(userRepository.existsByUsername(username)){
            username = username + "_" + genRandomString(5);
        }

        // SignUp guest
        var signupRequest = new SignupRequest();
        signupRequest.setPassword(password);
        signupRequest.setUsername(username);
        signupRequest.setGuest(true);
        signupRequest.setEmail(username + "@mail.ru");
        var roles = new HashSet<String>();
        roles.add("ROLE_USER");
        signupRequest.setRole(roles);


        var registerResponse = registerUser(signupRequest);
        if (registerResponse.getStatusCodeValue()!= 200)
            return registerResponse;

        // SignIn guest
        var loginRequest = new LoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);
        var loginResponse = authenticateUser(loginRequest);
        return loginResponse;
    }
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String jwt = jwtUtils.generateAccessToken(user);


        List<String> roles = user.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());



        return ResponseEntity.ok(new JwtResponse(jwt,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                roles));
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        user.setGuest(signUpRequest.isGuest());

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findRoleByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);


        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
