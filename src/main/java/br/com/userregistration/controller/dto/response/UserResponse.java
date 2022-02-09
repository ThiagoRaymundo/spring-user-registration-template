package br.com.userregistration.controller.dto.response;

import java.time.OffsetDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.userregistration.model.Phone;
import br.com.userregistration.model.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class UserResponse {

	private String id;

	private String name;

	private String email;

	private Set<Phone> phones;

	private Set<Role> roles;

	private OffsetDateTime created;

	private OffsetDateTime modified;	

}
