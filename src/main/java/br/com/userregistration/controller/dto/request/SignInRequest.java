package br.com.userregistration.controller.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SignInRequest {

	@Email
	@NotBlank
	private String email;

	@NotBlank
	@Size(min = 8, max = 10)
	private String password;

	public UsernamePasswordAuthenticationToken convert() {
		return new UsernamePasswordAuthenticationToken(email, password);
	}

}
