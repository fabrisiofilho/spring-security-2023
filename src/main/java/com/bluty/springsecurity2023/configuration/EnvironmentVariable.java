package com.bluty.springsecurity2023.configuration;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EnvironmentVariable {

    public static final String ENVIRONMENT = "dev";
    public static final String TOKEN_SECRET_PHRASE = "FraseSecretaDoSeifApi";
    public static final Long TOKEN_EXPIRATION = 6000000L;
    public static final Long EXPIRATION_REFRESH_TOKEN = 172800000L;

}