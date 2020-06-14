package kr.co.fastcampus.eatgo.util;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.jsonwebtoken.Claims;

class JwtProviderTest {

    private static final String SECRET = "secretsecretsecretsecretsecretsecretsecret";

    private JwtProvider jwtProvider;

    @BeforeEach
    void setUp() {
        jwtProvider = new JwtProvider(SECRET);
    }

    @Test
    void createToken() {
        String token = jwtProvider.createToken(1004L, "John");

        assertThat(token).contains(".");
    }

    @Test
    void getClaims() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEwMDQsInVzZXJOYW1lIjoidGVzdGVyIn0.bUqxLMPHoRhKqHFqOJp9BkbLn7Ym48k2b2XL2tPUSKU";
        Claims claims = jwtProvider.getClaims(token);

        assertAll(
            () -> assertThat(claims.get("userId", Long.class)).isEqualTo(1004L),
            () -> assertThat(claims.get("userName")).isEqualTo("tester")
        );
    }
}