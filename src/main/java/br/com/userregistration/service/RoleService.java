package br.com.userregistration.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.userregistration.model.Role;
import br.com.userregistration.repository.RoleRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    /**
     *  Find Role by name   
     * @param Role name
     * @return object found 
     */
    public Optional<Role> findByName(String name) {
        return this.repository.findByName(name);
    }

    /**
     * Find Role by name or insert when not found.     * 
     * @param Role name 
     * @return Object found or inserted. 
     */
    public Role findOrInsert(String name) {
        return this.findByName(name.toUpperCase())
               .orElse(this.repository.save(
                          new Role(null, name.toUpperCase())));

    }

}
