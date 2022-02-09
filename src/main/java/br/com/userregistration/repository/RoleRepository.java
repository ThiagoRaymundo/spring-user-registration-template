package br.com.userregistration.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.userregistration.model.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByName(String name);


    
}
