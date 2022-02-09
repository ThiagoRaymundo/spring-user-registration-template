package br.com.userregistration.service;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.userregistration.common.enums.SystemMessages;
import br.com.userregistration.common.validations.exceptions.BusinessException;
import br.com.userregistration.model.Role;
import br.com.userregistration.model.User;
import br.com.userregistration.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

	private UserRepository repository;

	private BCryptPasswordEncoder bCryptPasswordEncoder;

	private RoleService roleService;

	/**
	 * Private method. 
	 * Check if the email already exists in the registration.
	 * throw a BusinessExcepetion when the email is already in some record.
	 * @param Object User
	 */
	private void checkUserEmail(User user) {				
		var userFound = this.findByEmail(user.getEmail());		
		if (userFound != null) {
			throw new BusinessException(
					"[Email: " + user.getEmail()
							+ "] #"
							+ SystemMessages.OBJECT_IN_USE.getMsg());
			}		
	}

	/**
	 * Insert a new user
	 * @param Object User to be inserted
	 * @return Inserted user
	 */
	public User insert(User user) {
		this.checkUserEmail(user);
		if (user.getRoles().isEmpty()) {
			Role role = this.roleService.findOrInsert("ROLE_USER");
			user.addRole(role);
		}
		user.setId(null);
		user.setPassword(this.bCryptPasswordEncoder
				.encode(user.getPassword()));
		return this.repository.save(user);
	}

	/**
	 * User update
	 * @param Object User
	 * @return Updated user
	 */	
	public User update(User user) {
		user.setModified(OffsetDateTime.now()
				  .truncatedTo(ChronoUnit.MILLIS));		
		return this.repository.save(user);
	}

	/**
	 * User password update
	 * @param object user
	 * @return updated user
	 */
	public User updatePassword(User user) {
		user.setModified(OffsetDateTime.now());
		user.setPassword(this.bCryptPasswordEncoder
				.encode(user.getPassword()));
		return this.repository.save(user);
	}

	/**
	 * Find a User by email 
	 * @param Email
	 * @return User found or null if not found
	 */
	public User findByEmail(String email) {
		return this.repository.findByEmail(email)
				.orElse(null);
	}

	/**
	 * Find a User by id
	 * @param UUID id of the object
	 * @return Object found or null if not found 
	 */
	public User findById(UUID id) {
		return this.repository.findById(id)
				.orElse(null);

	}

}
