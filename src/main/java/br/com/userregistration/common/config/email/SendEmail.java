package br.com.userregistration.common.config.email;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import br.com.userregistration.common.validations.exceptions.EmailException;

@Service
@Profile("prod")
public class SendEmail implements NewEmail {

	@Autowired
	private JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String emailFrom;

	/**
	 * Send email
	 * 
	 * @param email address to which the one time password will be sent
	 * @param oneTimePassword
	 */
	@Override
	public void newEmail(String emailAddress, String oneTimePassword) {
		MimeMessage msg = mailSender.createMimeMessage();
		MimeMessageHelper msgHelp = new MimeMessageHelper(msg);
		try {
			msgHelp.setFrom(emailFrom, "Email Template");
			msgHelp.setTo(emailAddress);
			msgHelp.setSubject("Password assistance");
			String emailBody = "<p>Hello,<p>" 
			        + "<p> You have requested to reset your password.<p>"
					+ "<p>To authenticate, please use the following one time password:</p>" 
			        + "<p>" + oneTimePassword + "</p>";

			msgHelp.setText(emailBody, true);
			mailSender.send(msg);

		} catch (Exception e) {
			throw new EmailException("Error sending email: "
					+ e
					+ " "
					+ SendEmail.class.toString());
		}

	}

}
