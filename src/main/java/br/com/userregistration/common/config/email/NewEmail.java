package br.com.userregistration.common.config.email;

import org.springframework.stereotype.Component;

@Component
public interface NewEmail {
	
	void newEmail(String emailAddress, String oneTimePassword);

}
