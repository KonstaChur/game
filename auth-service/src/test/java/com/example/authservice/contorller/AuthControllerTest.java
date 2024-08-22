package com.example.authservice.contorller;

import com.example.authservice.contorller.AuthController.AuthError;
import com.example.authservice.model.User;
import com.example.authservice.service.JwtCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private JwtCreator jwtCreator;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_testMethod() {
        String response = authController.test();
        assertEquals("Тест прошел успешно", response);
    }

    @Test
    void test_authenticate_success() {
        String user = "User_123";
        String password = "123";
        String expectedJwt = "jwtToken";

        when(jwtCreator.createJwt(any(User.class))).thenReturn(expectedJwt);

        String jwt = authController.authenticate(user, password);

        assertEquals(expectedJwt, jwt);
        verify(jwtCreator, times(1)).createJwt(any(User.class));
    }

    @Test
    void test_authenticate_fail() {
        String user = "User_123";
        String password = "wrongPassword";

        Exception exception = assertThrows(AuthError.class, () -> {
            authController.authenticate(user, password);
        });

        String expectedMessage = "AuthError";
        String actualMessage = exception.getClass().getSimpleName();

        System.out.println(actualMessage);

        assertTrue(actualMessage.contains(expectedMessage));
        verify(jwtCreator, never()).createJwt(any(User.class));
    }

    @Test
    void test_validateJwt_success() {
        String token = "jwtToken";
        String user = "User_123";

        when(jwtCreator.validateJwt(user, token)).thenReturn(true);

        boolean isValid = authController.validateJwt(token, user);

        assertTrue(isValid);
        verify(jwtCreator, times(1)).validateJwt(user, token);
    }

    @Test
    void test_validateJwt_fail() {
        String token = "jwtToken";
        String user = "User_123";

        when(jwtCreator.validateJwt(user, token)).thenReturn(false);

        boolean isValid = authController.validateJwt(token, user);

        assertFalse(isValid);
        verify(jwtCreator, times(1)).validateJwt(user, token);
    }
}
