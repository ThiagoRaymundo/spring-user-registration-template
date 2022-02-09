package br.com.userregistration.controller.dto.request;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import br.com.userregistration.model.Phone;
import lombok.Getter;

@Getter
public class SignUpRequest {

	@NotBlank
	@Length(max = 255)
	private String name;

	@Email
	@NotBlank
	private String email;

	@NotBlank
	@Size(min = 8, max = 10)
	private String password;

	private Set<Phone> phones;

}
