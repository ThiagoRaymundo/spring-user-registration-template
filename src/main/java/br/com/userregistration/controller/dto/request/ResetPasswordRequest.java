package br.com.userregistration.controller.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;

@Getter
public class ResetPasswordRequest {

	@NotBlank
	@Size(max = 10)
	private String oneTimePassword;

	@NotBlank
	@Size(min = 8, max = 10)
	private String newPassword;

	

}
