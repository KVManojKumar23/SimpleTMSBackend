package com.snipe.service;

import org.springframework.web.multipart.MultipartFile;

import com.snipe.dto.user.UserDTO;
import com.snipe.dto.user.UserRegisterDTO;
import com.snipe.entity.User;

import io.jsonwebtoken.io.IOException;

import java.util.List;
import java.util.Optional;

public interface UserServices {
	
	//auth register 
	UserDTO registerUser(UserRegisterDTO userRegisterDTO);
    
    // Basic CRUD operations
    UserDTO createUser(User user);
    Optional<UserDTO> getUserById(Integer id);
    Optional<UserDTO> getUserByemail(String email);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(User user);
    void deleteUser(Integer id);
    
    //getUserByRoleID
    
    List<UserDTO> getUsersByRoleID(Integer ID);
    
    // Profile management
    UserDTO updateUserProfile(Integer userId, User profileDetails);
    
    // Simplified image handling
    String updateProfilePicture(Integer userId, MultipartFile imageFile) throws IOException,java.io.IOException;
    void removeProfilePicture(Integer userId);
    String getProfilePictureUrl(Integer userId);
    
    // Query operations
    Optional<UserDTO> getUserByEmail(String email);
    boolean existsByEmail(String email);
    
    // Status management
    void toggleUserStatus(Integer userId, boolean active);
    long countActiveUsers();
    
    boolean userExists(String email);

}

