package com.snipe.auth;

import com.snipe.dto.user.UserDTO;
import com.snipe.dto.user.UserRegisterDTO;
import com.snipe.entity.User;
import com.snipe.repository.UserRepository;
import com.snipe.service.UserServices;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
	

    private final UserRepository userRepository;
    private UserServices userServices;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    
    public AuthController(
    		UserRepository userRepository, 
    		UserServices userServices, 
    		PasswordEncoder passwordEncoder,
			JwtUtil jwtUtil
			) {
		super();
		this.userRepository = userRepository;
		this.userServices = userServices;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
	}

    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated @RequestBody UserRegisterDTO userRegisterDTO) {

        System.out.println("org id : "+ userRegisterDTO.getOrgID());

        // Validate password
        if (userRegisterDTO.getPassword() == null || userRegisterDTO.getPassword().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Password cannot be empty");
        }
        
        // Check if user already exists
        if (userServices.userExists(userRegisterDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                   .body("Email already registered");
        }
        
        // Call service to register the user and return UserDTO
        UserDTO createdUser = userServices.registerUser(userRegisterDTO);
        
        // Return created user with 201 status
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        Optional<User> dbUser = userRepository.findByEmail(request.getEmail());

        // Check if user exists
        if (dbUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "User not found!"));
        }

        User user = dbUser.get();

        // Debugging logs
        System.out.println("DEBUG: Found user - Email: " + user.getEmail());
        System.out.println("DEBUG: Hashed Password from DB: " + user.getPassword());
        System.out.println("DEBUG: Entered Password: " + request.getPassword());

        // Check if password matches
        boolean isPasswordMatch = passwordEncoder.matches(request.getPassword(), user.getPassword());
        System.out.println("DEBUG: Password Matched? " + isPasswordMatch);

        if (!isPasswordMatch) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "Incorrect password!"));
        }

        // Check if the user has a role assigned
        if (user.getRole() == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "User role is missing!"));
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().getRoleName());

        // Return successful response
        return ResponseEntity.ok(new AuthResponse("Bearer "+token, user.getRole().getRoleName()));
    }    
}
