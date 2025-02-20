package com.jarapplication.kiranastore.constants;

public class SecurityConstants {
    public static final String SECRET_KEY =
            "yourSuperSecretKey_yourSuperSecretKey_yourSuperSecretKey_yourSuperSecretKey";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final long ACCESS_TOKEN_EXPIRATION_TIME = 5 * 60 * 1000;
    public static final long REFRESH_TOKEN_EXPIRATION_TIME = 15 * 60 * 1000;
}
