package br.com.userregistration.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.userregistration.common.config.security.TokenService;
import br.com.userregistration.common.enums.SystemMessages;
import br.com.userregistration.common.validations.dto.StandardErrorResponse;
import br.com.userregistration.controller.dto.request.SignInRequest;
import br.com.userregistration.controller.dto.response.TokenResponse;
import br.com.userregistration.model.User;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("account/auth")
@AllArgsConstructor
public class AuthController {

	private TokenService tokenService;

	private AuthenticationManager authManager;

	@PostMapping("/signin")
	public ResponseEntity<?> authentication(@RequestBody @Valid SignInRequest request) {
		UsernamePasswordAuthenticationToken singIn = request.convert();
		try {
			Authentication auth = this.authManager.authenticate(singIn);
			User user = (User) auth.getPrincipal();
			String token = this.tokenService.generate(user);
			return ResponseEntity.ok().body(new TokenResponse(token));
		} catch (AuthenticationException e) {
			StandardErrorResponse error = new StandardErrorResponse();
			error.addStandrError(HttpStatus.BAD_REQUEST.value(),
					e.toString() + " " + SystemMessages.USERNAME_OR_PASSWORD_INVALID.getMsg());

			return ResponseEntity.badRequest().body(error);
		}

	}

}
