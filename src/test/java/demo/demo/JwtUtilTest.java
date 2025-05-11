package demo.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import demo.demo.security.JwtUtil;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private final String email = "usuario@correo.com";

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
    }

    @Test
    void generateToken_shouldReturnValidToken() {
        String token = jwtUtil.generateToken(email);
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    void extractEmail_shouldReturnCorrectEmail() {
        String token = jwtUtil.generateToken(email);
        String extractedEmail = jwtUtil.extractEmail(token);
        assertEquals(email, extractedEmail);
    }

    @Test
    void isTokenValid_shouldReturnTrueForValidToken() {
        String token = jwtUtil.generateToken(email);
        assertTrue(jwtUtil.isTokenValid(token));
    }

    @Test
    void isTokenValid_shouldReturnFalseForInvalidToken() {
        String fakeToken = "esto.no.es.un.token.valido";
        assertFalse(jwtUtil.isTokenValid(fakeToken));
    }
}
