package com.ecole.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecole.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
	Role findByName(String name);

}
