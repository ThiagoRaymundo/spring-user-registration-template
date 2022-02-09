package br.com.userregistration.common.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.userregistration.common.enums.SystemMessages;
import br.com.userregistration.repository.UserRepository;

@Service
public class AuthService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * login validation
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.userRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException(
						"Email: "
								+ username
								+ " "
								+ SystemMessages.OBJECT_NOT_FOUND.getMsg()));
	}

}
