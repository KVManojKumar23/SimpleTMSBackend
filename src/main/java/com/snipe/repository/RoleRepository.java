package com.snipe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.snipe.entity.Role;
import com.snipe.entity.Role.RoleType;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
	boolean existsByRoleName(Role.RoleType roleName); 
	
	Optional<Role> findByRoleName(RoleType roleName);
}
