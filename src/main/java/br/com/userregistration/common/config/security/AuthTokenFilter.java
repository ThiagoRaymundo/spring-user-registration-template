package br.com.userregistration.common.config.security;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import br.com.userregistration.model.User;
import br.com.userregistration.repository.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

    private TokenService tokenService;

    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = this.getToken(request);
        boolean validated = this.tokenService.isTokenValid(token);
        if (validated) {
            this.authenticateUser(token);
        }
        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }
        return token.substring(7, token.length());
    }

    private void authenticateUser(String token) {
        UUID userId = this.tokenService.getUserId(token);
        User user = this.userRepository.findById(userId).get();
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userId, null,
                user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);
    }

}
