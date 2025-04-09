package com.snipe.auth;

import com.snipe.entity.User;
import com.snipe.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	private final UserRepository userRepository;
	
    public UserDetailsServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}



	@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    	Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        User user = userOpt.get();
        
        // Ensure role is not null and is properly formatted
//        RoleType roleName = (user.getRole() != null && user.getRole().getRoleName() != null) 
//                ? user.getRole().getRoleName() 
//                : RoleType.USER;
        
        String role = (user.getRole() != null) ? user.getRole().getRoleName().name() : "USER"; 

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(role)) // Use List instead of singleton
        );
    }
	
	

}
