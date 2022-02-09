package br.com.userregistration.common.config.email;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class SendEmailMock implements NewEmail {

	@Override
	public void newEmail(String emailAddress, String oneTimePassword) {
		System.out.println("****** Email address provided: " + emailAddress + " ******");
		System.out.println("****** One time password: " + oneTimePassword + " ******");

	}

}
