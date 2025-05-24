package com.adriannebulao.tasktracker.security.jwt;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class SecurityConstants {

    @Value("${jwt.secret}")
    public String JWT_SECRET;
    public static final long JWT_EXPIRATION = 70000;
}
