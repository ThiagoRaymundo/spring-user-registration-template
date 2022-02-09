package br.com.userregistration.controller.dto.request;

import java.util.Set;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import br.com.userregistration.model.Phone;
import br.com.userregistration.model.User;
import lombok.Getter;

@Getter
public class UpdateUserRequest {

    @NotBlank
    @Length(max = 255)
    private String name;
    
    private Set<Phone> phones;

	public User completeUser(User user) {
		user.setName(name);
		user.setPhones(phones);
		return user;
	}

	
}
