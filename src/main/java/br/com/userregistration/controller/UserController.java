package br.com.userregistration.controller;

import java.net.URI;
import java.util.UUID;

import javax.validation.Valid;

import org.hibernate.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.userregistration.common.enums.SystemMessages;
import br.com.userregistration.controller.dto.request.SignUpRequest;
import br.com.userregistration.controller.dto.request.UpdateUserRequest;
import br.com.userregistration.controller.dto.response.UserResponse;
import br.com.userregistration.model.User;
import br.com.userregistration.service.UserService;
import lombok.AllArgsConstructor;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/account")
public class UserController {

	private UserService service;

	private ModelMapper modelMapper;

	@Transactional
	@PostMapping("/signup")
	public ResponseEntity<UserResponse> insert(@RequestBody @Valid SignUpRequest request,
			UriComponentsBuilder uriComponentsBuilder) {
		User user = this.modelMapper.map(request, User.class);
		User newUser = this.service.insert(user);
		UserResponse responseBody = this.modelMapper
				.map(newUser, UserResponse.class);
		URI uri = uriComponentsBuilder
				.path("/account/{id}")
				.buildAndExpand(newUser.getId()).toUri();
		return ResponseEntity.created(uri).body(responseBody);
	}

	@Transactional
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody @Valid UpdateUserRequest request) {
	   var userFound = this.service.findById(id);		
		if (userFound == null) {
			throw new ObjectNotFoundException(
					 SystemMessages.OBJECT_NOT_FOUND.getMsg(), 
					 id.toString() + " ");
		}    			
		User updatedUser = this.service.update(request.completeUser(userFound));
		UserResponse response = this.modelMapper
				 .map(updatedUser, UserResponse.class);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> findById(@PathVariable("id") UUID id) {
		User user = this.service.findById(id);
		if (user == null) {
			throw new ObjectNotFoundException(
					SystemMessages
					 .OBJECT_NOT_FOUND
					 .getMsg(), id.toString() + " ");
		}
		UserResponse response = this.modelMapper
				.map(user, UserResponse.class);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<UserResponse> findByEmail(@PathVariable("email") String email) {
		var user = this.service.findByEmail(email);
		if (user == null) {
			throw new ObjectNotFoundException(
					SystemMessages
					.OBJECT_NOT_FOUND
					.getMsg(), email.toString() + " ");
		}
		UserResponse response = this.modelMapper
				.map(user, UserResponse.class);
		return ResponseEntity.ok().body(response);
	}

}
