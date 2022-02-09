package br.com.userregistration.common.config.security;

import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import br.com.userregistration.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	@Value("${jwt.secretKey}")
	private String secretKey;

	@Value("${jwt.expiration}")
	private String expiration;

	public String generate(User user) {
		Date today = new Date();
		Date expirationDate = new Date(today.getTime() + Long.valueOf(expiration));
		return Jwts.builder().setIssuer("API RESTfull - User registration.").setSubject(user.getId().toString())
				.setIssuedAt(today).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
	}

	public boolean isTokenValid(String token) {
		try {
			Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public UUID getUserId(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
		return UUID.fromString(claims.getSubject().toString());

	}

}
