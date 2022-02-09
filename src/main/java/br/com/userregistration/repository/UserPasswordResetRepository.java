package br.com.userregistration.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import br.com.userregistration.model.UserPasswordReset;

public interface UserPasswordResetRepository extends JpaRepository<UserPasswordReset, Long> {

	Optional<UserPasswordReset> findByOneTimePassword(String oneTimePassword);

	

	

}
