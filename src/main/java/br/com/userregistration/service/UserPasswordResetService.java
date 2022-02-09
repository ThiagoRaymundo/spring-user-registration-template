package br.com.userregistration.service;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.userregistration.model.User;
import br.com.userregistration.model.UserPasswordReset;
import br.com.userregistration.repository.UserPasswordResetRepository;

@Service
public class UserPasswordResetService {

	@Autowired
	private UserPasswordResetRepository repository;

	@Value("${reset.password-token-expiration}")
	private String resetTokenExpiration;
	
	
	/**
	 * Generate one time password
	 * @param object user
	 * @return generated password to reset user password 
	 */
	public String generateOneTimePassword(User user) {
		UserPasswordReset userPasswordReset = new UserPasswordReset();
		int a = (int)(Math.random() * 9) + 1;
		String oneTimePassword = UUID.randomUUID()
				.toString().substring(0,10)
				.toUpperCase()
				.replace("-", String.valueOf(a));			
		userPasswordReset.setOneTimePassword(oneTimePassword);
		userPasswordReset.setExpirationDate(OffsetDateTime
				.now()
				.plus(Long.valueOf(this.resetTokenExpiration)
					   ,ChronoUnit.MILLIS));
		userPasswordReset.setUser(user);		
		return this.insert(userPasswordReset)
				.getOneTimePassword();
	}

	/**
	 * Insert new object UserPasswordReset
	 * @param object userPasswordReset
	 * @return inserted object userPasswordReset
	 */
	@Transactional
	public UserPasswordReset insert(UserPasswordReset userPasswordReset) {
		userPasswordReset.setId(null);
       	return this.repository.save(userPasswordReset);
	}

   /**
    * Find by one time password 
    * @param oneTimePassword
    * @return UserPasswordReset object found or null when not found
    */
	public UserPasswordReset findByOneTimePassword(String oneTimePassword) {
		return this.repository.findByOneTimePassword(oneTimePassword)
				.orElse(null);
		}

	/**
	 * Delete UserPasswordResert object by ID
	 * @param id object UserPasswordResert
	 */
	public void deleteById(Long id) {
		this.repository.deleteById(id);
		
	}

}
