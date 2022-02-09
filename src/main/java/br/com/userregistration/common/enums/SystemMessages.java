package br.com.userregistration.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SystemMessages {

	OBJECT_NOT_FOUND("Object not found!"), OBJECT_IN_USE("Object in use!"),
	USERNAME_OR_PASSWORD_INVALID("Username / Password invalid!"),
	PASSWORD_NOT_MATCH("Password and password confirmation do not match!"),
	RESET_PASSWORD_ERROR("Password reset error: "),
	FORGOT_PASSWORD("A one time password has been sent to the email address provided. Please check email!");

	private String msg;

}
