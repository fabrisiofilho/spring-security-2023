package com.bluty.springsecurity2023.dto.auth;

import com.bluty.springsecurity2023.dto.user.UserDTO;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    private String token;
    private String refreshToken;
    private UserDTO.Login user;

}
