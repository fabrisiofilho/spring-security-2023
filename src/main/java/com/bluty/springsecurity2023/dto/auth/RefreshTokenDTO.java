package com.bluty.springsecurity2023.dto.auth;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenDTO {

    private String token;
    private String refreshToken;
    private String email;

}
