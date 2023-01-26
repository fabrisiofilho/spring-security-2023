package com.bluty.springsecurity2023.security.detail;

import com.bluty.springsecurity2023.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthUser implements UserDetails {

    private UUID id;
    private String email;
    private String password;
    private Boolean enabled;
    private Collection<? extends GrantedAuthority> authorities;

    public AuthUser(UserEntity usuario) {
        super();
        this.id = usuario.getId();
        this.email = usuario.getEmail();
        this.password = usuario.getPassword();
        this.enabled = true;
        this.authorities = getAuthoritiesUser(usuario);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    private Collection<GrantedAuthority> getAuthoritiesUser(UserEntity user){
        Collection<GrantedAuthority> authory = new ArrayList<>();
        authory.add(new SimpleGrantedAuthority("ADMIN"));
        return authory;
    }


}
