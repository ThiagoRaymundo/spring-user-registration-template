package br.com.userregistration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport
public class UserRegistrationApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(UserRegistrationApplication.class, args);

	}

}
