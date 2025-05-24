package com.adriannebulao.tasktracker.security.jwt;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SecurityConstants {
    public static final String JWT_SECRET = "secret";
    public static final long JWT_EXPIRATION = 70000;
}
