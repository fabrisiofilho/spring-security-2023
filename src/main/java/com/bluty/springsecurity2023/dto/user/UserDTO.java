package com.bluty.springsecurity2023.dto.user;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private UUID id;

    private String fullName;

    private String username;

    private String email;

    private String urlPhoto;

    private String password;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Credential {
        private String email;
        private String password;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Login {
        private String fullName;

        private String username;

        private String email;

        private String urlPhoto;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Register {
        private String fullName;
        private String email;
        private String password;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdatePhoto {
        private UUID id;
        private MultipartFile multipartFile;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateName {
        private UUID id;
        private String fullName;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateUser {
        private UUID id;
        private String fullName;
        private String password;
        private String email;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Recover {
        private String email;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResetPassword {
        private String email;
        private String password;
        private String token;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Email {
        private String email;
    }

}