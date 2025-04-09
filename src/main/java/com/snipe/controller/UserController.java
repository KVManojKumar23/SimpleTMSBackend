package com.snipe.controller;


import com.snipe.dto.user.UserDTO;
import com.snipe.entity.User;
import com.snipe.service.UserServices;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServices userServices;

    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userServices.createUser(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {
        Optional<UserDTO> userDTO = userServices.getUserById(id);
        return userDTO.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        Optional<UserDTO> userDTO = userServices.getUserByemail(email);
        return userDTO.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userServices.getAllUsers());
    }

    @PutMapping("/update")
    public ResponseEntity<UserDTO> updateUser(@RequestBody User user) {
        return ResponseEntity.ok(userServices.updateUser(user));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        userServices.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PutMapping("/{userId}/update-profile")
    public ResponseEntity<UserDTO> updateProfile(@PathVariable Integer userId, @RequestBody User user) {
        return ResponseEntity.ok(userServices.updateUserProfile(userId, user));
    }

    @PostMapping("/{userId}/upload-profile-picture")
    public ResponseEntity<String> uploadProfilePicture(@PathVariable Integer userId, @RequestParam("imageFile") MultipartFile imageFile) throws Exception {
        return ResponseEntity.ok(userServices.updateProfilePicture(userId, imageFile));
    }

    @DeleteMapping("/{userId}/remove-profile-picture")
    public ResponseEntity<String> removeProfilePicture(@PathVariable Integer userId) {
        userServices.removeProfilePicture(userId);
        return ResponseEntity.ok("Profile picture removed successfully");
    }

    @GetMapping("/{userId}/profile-picture-url")
    public ResponseEntity<String> getProfilePictureUrl(@PathVariable Integer userId) {
        return ResponseEntity.ok(userServices.getProfilePictureUrl(userId));
    }
}