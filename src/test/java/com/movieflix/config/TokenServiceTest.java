package com.movieflix.config;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TokenServiceTest {

        @Test
        void shouldRejectTokenWithWrongIssuer() {
            TokenService tokenService = new TokenService();
            String secret = "test-secret-key-at-least-32-characters";

            ReflectionTestUtils.setField(tokenService, "secret", secret);

            String token = JWT.create()
                    .withSubject("test@example.com")
                    .withIssuer("Wrong Issuer")
                    .sign(Algorithm.HMAC256(secret));

            assertTrue(tokenService.verifyToken(token).isEmpty());
        }
    }