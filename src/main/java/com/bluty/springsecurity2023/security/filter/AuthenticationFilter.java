package com.bluty.springsecurity2023.security.filter;

import com.bluty.springsecurity2023.dto.auth.LoginDTO;
import com.bluty.springsecurity2023.dto.user.UserDTO;
import com.bluty.springsecurity2023.entity.UserEntity;
import com.bluty.springsecurity2023.security.detail.AuthUser;
import com.bluty.springsecurity2023.security.util.TokenJWTSecurity;
import com.bluty.springsecurity2023.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final TokenJWTSecurity jwtSecurity;
    private final UserService userService;


    public AuthenticationFilter(AuthenticationManager authenticationManager, TokenJWTSecurity jwtSecurity, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtSecurity = jwtSecurity;
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserDTO.Credential credenciaisDTO = new ObjectMapper().readValue(request.getInputStream(), UserDTO.Credential.class);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(credenciaisDTO.getEmail(), credenciaisDTO.getPassword(), new ArrayList<>());
            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        String username = ((AuthUser) authResult.getPrincipal()).getUsername();
        String token = jwtSecurity.generateToken(username);
        String refreshToken = jwtSecurity.generateRefreshToken(username);

        UserEntity userEntity = userService.findByEmail(username);

        LoginDTO login = LoginDTO.builder()
                .token(token)
                .refreshToken(refreshToken)
                .user(new ModelMapper().map(userEntity, UserDTO.Login.class))
                .build();

        try {
            String json = new ObjectMapper().writer().writeValueAsString(login);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(json);
            response.getWriter().flush();
        } catch (Exception e) {
            Logger logger = LoggerFactory.getLogger("global");
            logger.warn("A WARN Message");
        }

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        String json = new ObjectMapper().writer().writeValueAsString("Não foi possivel autenticar.");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(json);
        response.getWriter().flush();
        response.setStatus(HttpStatus.FORBIDDEN.value());
    }

}
