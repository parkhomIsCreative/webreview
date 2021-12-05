package com.example.webrewiew.security;

public class SecurityConstants {
    public static final String SIGN_UP_URLS = "/api/auth/**";
    public static final String GET_POST_URL = "/api/post/all";
    public static final String GET_IMAGE_URL = "/api/image/**";
    public static final String GET_COMMENT_URL = "/api/comment/**";
    public static final String SECRET = "SecretKeyGenJWT";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String CONTENT_TYPE = "application/json";
    public static final long EXPIRATION_TIME = 600_000;
}
