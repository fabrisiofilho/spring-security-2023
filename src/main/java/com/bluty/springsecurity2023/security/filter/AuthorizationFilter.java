package com.bluty.springsecurity2023.security.filter;

import com.bluty.springsecurity2023.entity.UserEntity;
import com.bluty.springsecurity2023.security.util.TokenJWTSecurity;
import com.bluty.springsecurity2023.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    private final TokenJWTSecurity jwtSecurity;
    private final UserService userService;
    private final UserDetailsService userDetailsService;

    private static final String[] URL_IGNORE = { "/auth/**", "/health/**" };

    public AuthorizationFilter(AuthenticationManager authenticationManager, TokenJWTSecurity jwtSecurity,
                               UserService userService, UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.jwtSecurity = jwtSecurity;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException {
        String header = request.getHeader("AUTHORIZATION");

        try {
            if (Objects.nonNull(header)) {
                UsernamePasswordAuthenticationToken token = getAuthentication(header);
                if (token != null) {
                    SecurityContextHolder.getContext().setAuthentication(token);
                }
            }
            chain.doFilter(request, response);
        } catch (Exception exception) {
            SecurityContextHolder.clearContext();
            String json = new ObjectMapper().writer().writeValueAsString(exception.getMessage());
            response.getWriter().write(json);
            response.getWriter().flush();
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return Arrays.stream(URL_IGNORE).anyMatch(p -> pathMatcher.match(p, request.getRequestURI()));
    }

    @Override
    protected boolean isIgnoreFailure() {
        return true;
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if (jwtSecurity.tokenValid(token)) {
            String email = jwtSecurity.getUserEmail(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            UserEntity user = userService.findByEmail(email);
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        }
        return null;
    }

}
