package com.bluty.springsecurity2023.security.detail;

import com.bluty.springsecurity2023.entity.UserEntity;
import com.bluty.springsecurity2023.exception.AuthenticationException;
import com.bluty.springsecurity2023.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity usuario = findUserByEmail(username);
        if (Objects.isNull(usuario)) {
            usuario = findUserByUsername(username);
        }
        if (Objects.isNull(usuario)) {
            throw new UsernameNotFoundException("Usuário não encontrado com e-mail informado");
        }
        return new AuthUser(usuario);
    }

    public AuthUser userLogged() {
        try {
            return (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new AuthenticationException(e.getMessage(), e);
        }
    }

    private UserEntity findUserByEmail(String email) {
        try {
            return userService
                    .findByEmail(email);
        } catch(Exception e) {

            return null;
        }
    }


    private UserEntity findUserByUsername(String username) {
        try {
            return userService
                    .findByUsername(username);
        } catch(Exception e) {
            return null;
        }
    }

}
