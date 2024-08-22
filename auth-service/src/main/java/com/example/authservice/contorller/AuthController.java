package com.example.authservice.contorller;

import com.example.authservice.model.User;
import com.example.authservice.service.JwtCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private static final List<User> users = List.of(
            new User().setName("User_123").setPassword("123"),
            new User().setName("User_2").setPassword("123").setPlayer(true));

    private final JwtCreator jwtCreator;

    @GetMapping("/test")
    public String test(){
        return "Тест прошел успешно";
    }

    @GetMapping("/authenticate")
    public String authenticate(@RequestParam String user, @RequestParam String password) {
        log.info("запрос");
        return users.stream()
                .filter(e -> e.getName().equals(user) && e.getPassword().equals(password))
                .findFirst()
                .map(jwtCreator::createJwt)
                .orElseThrow(AuthError::new);
    }

    @GetMapping("/validate")
    public boolean validateJwt(@RequestParam String token, @RequestParam String user) {
        log.info("проверка");
        return jwtCreator.validateJwt(user, token);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    public static class AuthError extends RuntimeException {}
}
