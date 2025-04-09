package com.snipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.snipe.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // Find by email (custom query method)
    Optional<User> findByEmail(String email);

    // Find all users by active status
    List<User> findAllByActive(boolean active);
   
    // Count users by status
    long countByActive(boolean active);

    // Check if user exists by email
    boolean existsByEmail(String email);
    
    //Find all User By Role ID
    List<User> findAllByRoleRoleId(Integer roleId);
}
