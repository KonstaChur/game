package com.example.gameservice.context;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class CommandContext {
    private String commandData;
}
