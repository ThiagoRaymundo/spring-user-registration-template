package br.com.userregistration.controller;

import javax.validation.Valid;
import javax.validation.constraints.Email;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.userregistration.common.config.email.NewEmail;
import br.com.userregistration.common.enums.SystemMessages;
import br.com.userregistration.common.validations.dto.StandardErrorResponse;
import br.com.userregistration.controller.dto.request.ResetPasswordRequest;
import br.com.userregistration.controller.dto.response.MessageResponse;
import br.com.userregistration.model.User;
import br.com.userregistration.model.UserPasswordReset;
import br.com.userregistration.service.UserPasswordResetService;
import br.com.userregistration.service.UserService;
import lombok.AllArgsConstructor;

@RestController
@Validated
@AllArgsConstructor
@RequestMapping("/account")
public class UserPasswordResetController {

	private UserService userService;

	private UserPasswordResetService service;
	
	private NewEmail sendEmail;

	
	@PostMapping("/forgotPassword")
	public ResponseEntity<MessageResponse> forgotPassword(UriComponentsBuilder uriComponentsBuilder,
			@RequestParam("email") @Email String email) {
		User userFound = this.userService.findByEmail(email);
		if (userFound == null) {
			return ResponseEntity.notFound().build();
		}
		String oneTimePassword = this.service.generateOneTimePassword(userFound);
		this.sendEmail.newEmail(email, oneTimePassword);
		return ResponseEntity.ok()
				.body(new MessageResponse(
						SystemMessages
						.FORGOT_PASSWORD
						.getMsg()));
	}

	@Transactional
	@PutMapping("/resetPassword")
	public ResponseEntity<?> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
		UserPasswordReset resetUserPassword = 
				this.service.findByOneTimePassword(request.getOneTimePassword());				
		if (resetUserPassword == null) {
			return ResponseEntity
					.badRequest()
					.body(new StandardErrorResponse(
							HttpStatus.BAD_REQUEST.value(),
					        SystemMessages.RESET_PASSWORD_ERROR
					        .getMsg() + "Invalid One time password! "));
		}
		if (resetUserPassword.isExpired()) {
			return ResponseEntity
					.badRequest()
					.body(new StandardErrorResponse(
							HttpStatus.BAD_REQUEST.value(),
					        SystemMessages.RESET_PASSWORD_ERROR
					        .getMsg() + "Expired One time password! "));
		}
		User user = resetUserPassword.getUser();
		user.setPassword(request.getNewPassword());
		this.userService.updatePassword(user);
		this.service.deleteById(
				resetUserPassword.getId());/* delete the record to prevent it from being used more than once */

		return ResponseEntity.noContent().build();
	}

}
