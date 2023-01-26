package com.bluty.springsecurity2023.entity;

import com.bluty.springsecurity2023.dto.user.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "sys_user")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "uuid", unique = true, nullable = false, updatable = false)
    private UUID id;

    @Column(name = "full_name")
    private String fullName;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(name = "token_recover")
    private String tokenRecover;

    public UserEntity update(UserDTO userDto){
        this.fullName = userDto.getFullName();
        this.username = userDto.getUsername();
        this.email = userDto.getEmail();
        return this;
    }

}
