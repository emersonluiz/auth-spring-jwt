package br.com.emersonluiz.security;

public class SecurityConstants {

    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final String SECRET_RECOVER = "RecoverSecretKeyJWTs";
    public static final long EXPIRATION_TIME = 120_000; // 2 minutes //864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_AUTH_TOKEN = "x-auth-token";
    public static final String SIGN_UP_URL = "/users/sign-up";
    public static final String RECOVER_URL = "/recovers/**";

}
