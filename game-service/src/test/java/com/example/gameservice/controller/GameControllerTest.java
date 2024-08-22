//package com.example.gameservice.controller;
//
//import com.example.gameservice.service.AsyncCommandExecutorService;
//import com.example.gameservice.service.TokenValidationService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.ResponseEntity;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.when;
//
//class GameControllerTest {
//
//    @Mock
//    private AsyncCommandExecutorService commandExecutorService;
//
//    @Mock
//    private TokenValidationService validationService;
//
//    @InjectMocks
//    private GameController gameController;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testExecuteCommandsWithoutAuthorizationHeader() {
//        ResponseEntity<String> response = gameController.executeCommands(null, "{}");
//
//        assertEquals(400, response.getStatusCodeValue());
//        assertEquals("The authorization header is missing or invalid.", response.getBody());
//    }
//
//    @Test
//    void testExecuteCommandsWithInvalidAuthorizationHeader() {
//        when(validationService.validateToken(anyString())).thenReturn(null);
//
//        ResponseEntity<String> response = gameController.executeCommands("invalid-token", "{}");
//
//        assertEquals(400, response.getStatusCodeValue());
//        assertEquals("The authorization header is invalid.", response.getBody());
//    }
//
//    @Test
//    void testExecuteCommandsWithValidAuthorizationHeader() {
//        when(validationService.validateToken(anyString())).thenReturn("testUser");
//        doNothing().when(commandExecutorService).executeCommandsAsync(anyString(), anyString());
//
//        ResponseEntity<String> response = gameController.executeCommands("valid-token", "{}");
//
//        assertEquals(200, response.getStatusCodeValue());
//        assertEquals("The command was executed successfully.", response.getBody());
//    }
//}
