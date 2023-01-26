package com.bluty.springsecurity2023.dto.auth;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordDTO {

    private String email;

}
