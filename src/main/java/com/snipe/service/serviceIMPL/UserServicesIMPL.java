package com.snipe.service.serviceIMPL;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.snipe.config.AppConfig;
import com.snipe.dto.user.UserDTO;
import com.snipe.dto.user.UserDTO.OrganizationDTO;
import com.snipe.dto.user.UserDTO.RoleDTO;
import com.snipe.dto.user.UserRegisterDTO;
import com.snipe.entity.Organization;
import com.snipe.entity.Role;
import com.snipe.entity.User;
import com.snipe.repository.OrganizationRepository;
import com.snipe.repository.RoleRepository;
import com.snipe.repository.UserRepository;
import com.snipe.service.UserServices;

import io.jsonwebtoken.io.IOException;

@Service
public class UserServicesIMPL implements UserServices{
	
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final OrganizationRepository organizationRepository;
	private final AppConfig appConfig;
	private final PasswordEncoder passwordEncoder;
	
	public UserServicesIMPL(
			UserRepository userRepository, 
			RoleRepository roleRepository,
			OrganizationRepository organizationRepository, 
			AppConfig appConfig,
			PasswordEncoder passwordEncoder
			) {
		super();
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.organizationRepository = organizationRepository;
		this.appConfig = appConfig;
		this.passwordEncoder = passwordEncoder;
	}

	private UserDTO convertToDTO(User user) {
	        UserDTO userDTO = new UserDTO();
	        
	        // Basic fields
	        userDTO.setUserId(user.getUserId());
	        userDTO.setFirstName(user.getFirstName());
	        userDTO.setLastName(user.getLastName());
	        userDTO.setFullName(user.getFullName());
	        userDTO.setEmail(user.getEmail());
	        userDTO.setPhoneNumber(user.getPhoneNumber());
	        userDTO.setAddress(user.getAddress());
	        userDTO.setGender(user.getGender());
	        userDTO.setDateOfBirth(user.getDateOfBirth());
	        userDTO.setImageUrl(user.getImageUrl());
	        userDTO.setActive(user.isActive());
	        userDTO.setCreatedAt(user.getCreatedAt());
	        userDTO.setLastLogin(user.getLastLogin());
	        
	        // Organization mapping
	        if (user.getOrganization() != null) {
	            OrganizationDTO orgDTO = new OrganizationDTO();
	            orgDTO.setOrgId(user.getOrganization().getOrgId());
	            orgDTO.setOrgName(user.getOrganization().getOrgName());
	            userDTO.setOrganization(orgDTO);
	        }
	        
	        // Role mapping
	        if (user.getRole() != null) {
	            RoleDTO roleDTO = new RoleDTO();
	            roleDTO.setRoleId(user.getRole().getRoleId());
	            roleDTO.setRoleName(user.getRole().getRoleName().name());
	            userDTO.setRole(roleDTO);
	        } else {
	        	RoleDTO roleDTO = new RoleDTO();
	        	roleDTO.setRoleId(5);
	        	roleDTO.setRoleName("USER");
	        	userDTO.setRole(roleDTO);
	        }
	        
	        return userDTO;
	    }

	    // Separate method for file upload logic
	    private String uploadFile(MultipartFile imageFile, Integer userId) throws IOException, IllegalStateException, java.io.IOException {
	        // Retrieve the upload directory path dynamically from AppConfig
	        String uploadDir = appConfig.getUploadDir();  // Use the uploadDir from AppConfig

	        // Create directory if it doesn't exist
	        File directory = new File(uploadDir);
	        if (!directory.exists()) {
	            directory.mkdirs();
	        }

	        // Generate a unique filename (e.g., userId + timestamp) to avoid name conflicts
	        String fileName = userId + "_" + System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();

	        // Create the file path
	        File destinationFile = new File(uploadDir + File.separator + fileName);  // Using File.separator for cross-platform compatibility

	        // Transfer the file to the destination location
	        imageFile.transferTo(destinationFile);

	        // Return the URL or relative path to the uploaded image
	        return appConfig.getBaseUrl() + fileName;  // Adjust the URL as per your requirements
	    }

	    @Override
	    public UserDTO createUser(User user) {
	        // Handling Organization and Role mapping
	        if (user.getOrganization() != null) {
	            // Ensure Organization is set properly
	            Organization organization = organizationRepository.findById(user.getOrganization().getOrgId())
	                    .orElseThrow(() -> new RuntimeException("Organization not found"));
	            user.setOrganization(organization);
	        }

	        if (user.getRole() != null) {
	            // Ensure Role is set properly
	            Role role = roleRepository.findById(user.getRole().getRoleId())
	                    .orElseThrow(() -> new RuntimeException("Role not found"));
	            user.setRole(role);
	        }

	        // Save user to the database
	        User savedUser = userRepository.save(user);

	        // Convert saved user to UserDTO
	        return convertToDTO(savedUser);
	    }

	    @Override
	    public Optional<UserDTO> getUserById(Integer id) {
	        // Fetch the User from the database
	        Optional<User> userOptional = userRepository.findById(id);
	        
	        // Check if the user exists, and convert to UserDTO
	        return userOptional.map(this::convertToDTO);
	    }
	    
	    @Override
		public Optional<UserDTO> getUserByemail(String email) {
	    	Optional<User> userOptional = userRepository.findByEmail(email);
	        
	        // Check if the user exists, and convert to UserDTO
	        return userOptional.map(this::convertToDTO);
		}
	    
	    @Override
	    public List<UserDTO> getAllUsers() {
	        // Fetch all users from the database
	        List<User> users = userRepository.findAll();
	        
	        // Convert the list of User entities to a list of UserDTOs
	        return users.stream()
	                    .map(this::convertToDTO)
	                    .collect(Collectors.toList());
	    }

	    @Override
	    public UserDTO updateUser(User user) {
	        // Check if the user exists
	        Optional<User> existingUserOptional = userRepository.findById(user.getUserId());
	        
	        if (existingUserOptional.isPresent()) {
	            // Get the existing user
	            User existingUser = existingUserOptional.get();
	            
	            // Update the fields of the existing user with the new values from the provided user
	            existingUser.setFirstName(user.getFirstName());
	            existingUser.setLastName(user.getLastName());
	            existingUser.setEmail(user.getEmail());
	            existingUser.setPhoneNumber(user.getPhoneNumber());
	            existingUser.setAddress(user.getAddress());
	            existingUser.setGender(user.getGender());
	            existingUser.setDateOfBirth(user.getDateOfBirth());
	            existingUser.setImageUrl(user.getImageUrl());
	            existingUser.setActive(user.isActive());
	            existingUser.setLastLogin(user.getLastLogin());
	            
	            // Optional: Handle the Organization and Role updates if necessary
	            if (user.getOrganization() != null) {
	                Organization organization = organizationRepository.findById(user.getOrganization().getOrgId())
	                        .orElseThrow(() -> new RuntimeException("Organization not found"));
	                existingUser.setOrganization(organization);
	            }
	            
	            if (user.getRole() != null) {
	                Role role = roleRepository.findById(user.getRole().getRoleId())
	                        .orElseThrow(() -> new RuntimeException("Role not found"));
	                existingUser.setRole(role);
	            }
	            
	            // Save the updated user
	            User updatedUser = userRepository.save(existingUser);
	            
	            // Convert and return the updated user as a DTO
	            return convertToDTO(updatedUser);
	        } else {
	            // Handle user not found (could throw an exception or return a custom error)
	            throw new RuntimeException("User not found with id: " + user.getUserId());
	        }
	    }

		@Override
		public void deleteUser(Integer id) {
		    // Check if the user exists
		    Optional<User> userOptional = userRepository.findById(id);
		    
		    // If user exists, delete it
		    if (userOptional.isPresent()) {
		        userRepository.deleteById(id);
		    } else {
		        throw new RuntimeException("User not found with id: " + id);
		    }
		}

		@Override
		public UserDTO updateUserProfile(Integer userId, User profileDetails) {
		    // Retrieve the existing user by userId
		    Optional<User> existingUserOptional = userRepository.findById(userId);
		    
		    if (existingUserOptional.isPresent()) {
		        // Get the existing user entity
		        User existingUser = existingUserOptional.get();
		        
		        // Update the user profile fields with the new details
		        existingUser.setFirstName(profileDetails.getFirstName());
		        existingUser.setLastName(profileDetails.getLastName());
		        existingUser.setPhoneNumber(profileDetails.getPhoneNumber());
		        existingUser.setAddress(profileDetails.getAddress());
		        existingUser.setGender(profileDetails.getGender());
		        existingUser.setDateOfBirth(profileDetails.getDateOfBirth());
		        existingUser.setImageUrl(profileDetails.getImageUrl());
		        
		        // Save the updated user entity
		        User updatedUser = userRepository.save(existingUser);
		        
		        // Convert the updated user entity to UserDTO and return it
		        return convertToDTO(updatedUser);
		    } else {
		        // Handle the case where the user is not found
		        throw new RuntimeException("User not found with id: " + userId);
		    }
		}

		@Override
		public String updateProfilePicture(Integer userId, MultipartFile imageFile) throws IOException, IllegalStateException, java.io.IOException {
		    // Retrieve the existing user by userId
		    Optional<User> existingUserOptional = userRepository.findById(userId);
		    
		    if (existingUserOptional.isPresent()) {
		        // Get the existing user entity
		        User existingUser = existingUserOptional.get();
		        
		        // Ensure the file is not empty
		        if (imageFile.isEmpty()) {
		            throw new IOException("File is empty");
		        }
		        
		        // Define the directory to store the image (you can change this path based on your application setup)
		        String uploadDir = "uploads/profile_pictures/";
		        File directory = new File(uploadDir);
		        
		        // Create directory if it doesn't exist
		        if (!directory.exists()) {
		            directory.mkdirs();
		        }
		        
		     // Use the uploadFile method to handle the file upload
	            String imageUrl = uploadFile(imageFile, userId);
	            
		        existingUser.setImageUrl(imageUrl);
		        
		        // Save the updated user entity
		        userRepository.save(existingUser);
		        
		        // Return the URL of the updated profile picture
		        return imageUrl;
		    } else {
		        // Handle the case where the user is not found
		        throw new RuntimeException("User not found with id: " + userId);
		    }
		}

		@Override
		public void removeProfilePicture(Integer userId) {
		    Optional<User> existingUserOptional = userRepository.findById(userId);

		    if (existingUserOptional.isPresent()) {
		        User existingUser = existingUserOptional.get();
		        String imageUrl = existingUser.getImageUrl();

		        if (imageUrl != null && !imageUrl.isEmpty()) {
		            File imageFile = new File(imageUrl); // assuming imageUrl is a local path

		            if (imageFile.exists()) {
		                imageFile.delete(); // delete file from disk
		            }

		            existingUser.setImageUrl(null); // remove imageUrl from DB
		            userRepository.save(existingUser);
		        } else {
		            throw new RuntimeException("Profile picture not set for user with id: " + userId);
		        }
		    } else {
		        throw new RuntimeException("User not found with id: " + userId);
		    }
		}

		@Override
		public String getProfilePictureUrl(Integer userId) {
		    // Retrieve the existing user by userId
		    Optional<User> existingUserOptional = userRepository.findById(userId);

		    if (existingUserOptional.isPresent()) {
		        // Get the existing user entity
		        User existingUser = existingUserOptional.get();

		        // Return the imageUrl if it is set, otherwise return null
		        return existingUser.getImageUrl(); // Will return null if imageUrl is not set
		    } else {
		        // Handle the case where the user is not found
		        throw new RuntimeException("User not found with id: " + userId);
		    }
		}

		@Override
		public Optional<UserDTO> getUserByEmail(String email) {
		    // Retrieve the user by email using the repository
		    Optional<User> userOptional = userRepository.findByEmail(email);

		    // If the user is found, convert it to a UserDTO and return it wrapped in Optional
		    if (userOptional.isPresent()) {
		        User user = userOptional.get();
		        UserDTO userDTO = convertToDTO(user); // Use your existing convertToDTO method
		        return Optional.of(userDTO);
		    } else {
		        // If the user is not found, return an empty Optional
		        return Optional.empty();
		    }
		}
		
		@Override
		public boolean existsByEmail(String email) {
		    return userRepository.existsByEmail(email);  // Calls the method from UserRepository
		}

		@Override
		public void toggleUserStatus(Integer userId, boolean active) {
		    // Retrieve the existing user by userId
		    Optional<User> userOptional = userRepository.findById(userId);

		    // Check if the user is present
		    if (userOptional.isPresent()) {
		        // Get the existing user entity
		        User user = userOptional.get();

		        // Update the active status
		        user.setActive(active);

		        // Save the updated user entity back to the repository
		        userRepository.save(user);
		    } else {
		        // Handle the case where the user is not found
		        throw new RuntimeException("User not found with id: " + userId);
		    }
		}

		@Override
		public long countActiveUsers() {
		    // Call the repository method to count the active users
		    return userRepository.countByActive(true);
		}

		@Override
		public boolean userExists(String email) {
		    // Check if the user with the provided email exists
		    Optional<User> user = userRepository.findByEmail(email);

		    // Return true if user exists, otherwise false
		    return user.isPresent();
		}

		@Override
		public UserDTO registerUser(UserRegisterDTO userRegisterDTO) {
		    // Check if the user already exists
		    if (userRepository.existsByEmail(userRegisterDTO.getEmail())) {
		        throw new RuntimeException("Email already registered");  // You can use a custom exception
		    }

		    // Convert UserRegisterDTO to User entity
		    User user = new User();
		    user.setFirstName(userRegisterDTO.getFirstName());
		    user.setLastName(userRegisterDTO.getLastName());
		    user.setEmail(userRegisterDTO.getEmail());
		    user.setPassword(this.passwordEncoder.encode(userRegisterDTO.getPassword())); // Encrypt the password
		    user.setPhoneNumber(userRegisterDTO.getPhoneNumber());
		    user.setAddress(userRegisterDTO.getAddress());
		    user.setGender(userRegisterDTO.getGender());
		    user.setDateOfBirth(userRegisterDTO.getDateOfBirth());
		    user.setActive(true);  // Setting default status as active
		    user.setCreatedAt(LocalDateTime.now());
		    user.setOrganization(null);
		    
		    if (userRegisterDTO.getOrgID() != null) {
		        Organization organization = organizationRepository.findById(userRegisterDTO.getOrgID())
		            .orElseThrow(() -> new RuntimeException("Organization not found"));
		        user.setOrganization(organization);
		    } else {
		        user.setOrganization(null);
		    }
		    Role role = roleRepository.findById(5) // Fetch from DB
	                   .orElseThrow(() -> new RuntimeException("Default role USER not found"));
		    user.setRole(role);
	    
		    

		    // Save User entity
		    User savedUser = userRepository.save(user);

		    // Convert saved User entity to UserDTO
		    UserDTO userDTO = new UserDTO();
		    userDTO.setUserId(savedUser.getUserId());
		    userDTO.setFirstName(savedUser.getFirstName());
		    userDTO.setLastName(savedUser.getLastName());
		    userDTO.setFullName(savedUser.getFullName());
		    userDTO.setEmail(savedUser.getEmail());
		    userDTO.setPhoneNumber(savedUser.getPhoneNumber());
		    userDTO.setAddress(savedUser.getAddress());
		    userDTO.setGender(savedUser.getGender());
		    userDTO.setDateOfBirth(savedUser.getDateOfBirth());
		    userDTO.setActive(savedUser.isActive());
		    userDTO.setCreatedAt(savedUser.getCreatedAt());
		    // Map Role to DTO
		    RoleDTO roleDTO = new RoleDTO();
		    roleDTO.setRoleId(savedUser.getRole().getRoleId());
		    roleDTO.setRoleName(savedUser.getRole().getRoleName().name());
		    userDTO.setRole(roleDTO);
		    // Map Organization to DTO		    
		    OrganizationDTO organizationDTO = new OrganizationDTO();
		    organizationDTO.setOrgId(savedUser.getOrganization().getOrgId());
		    organizationDTO.setOrgName(savedUser.getOrganization().getOrgName());
		    userDTO.setOrganization(organizationDTO);

		    return userDTO;
		}

		@Override
		public List<UserDTO> getUsersByRoleID(Integer ID) {
			// TODO Auto-generated method stub
			List<User> users = userRepository.findAllByRoleRoleId(ID);
			return users.stream().map(this::convertToDTO).collect(Collectors.toList());
		}

		

		
}
