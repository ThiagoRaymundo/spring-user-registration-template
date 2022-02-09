package br.com.userregistration.common.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.userregistration.repository.UserRepository;
import lombok.AllArgsConstructor;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private AuthService authService;

	private TokenService tokenService;

	private UserRepository userRepository;

	/**
	 * inject BCryptPasswordEncoder.
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * inject AuthenticationManager.
	 */
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	/*
	 * Authentication Configuration...access control.
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(authService).passwordEncoder(this.bCryptPasswordEncoder());
	}

	/*
	 * Endpoints access.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		        .antMatchers(HttpMethod.POST, "/account/auth/signin").permitAll()
				.antMatchers(HttpMethod.POST, "/account/signup").permitAll()
				.antMatchers(HttpMethod.POST, "/account/forgotPassword").permitAll()
				.antMatchers(HttpMethod.PUT, "/account/resetPassword").permitAll()
				.anyRequest().authenticated().and().csrf().disable()
				.addFilterBefore(new AuthTokenFilter(this.tokenService, this.userRepository),
						UsernamePasswordAuthenticationFilter.class)
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	}

	/*
	 * Static resource configuration (js, css, swagger...)
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/h2-console/**");
	}

}
