//package com.example.gameservice.command;
//
//import jakarta.annotation.PostConstruct;
//import lombok.Getter;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.util.UUID;
//
//@Getter
//@Component("createGame")
//@Slf4j
//public class CreateGame implements ICommand {
//    private String gameId;
//
//    @Value("${value}")
//    private int value;
//    private int a = 1;
//
//    @PostConstruct
//    private void init() {
//        this.gameId = UUID.randomUUID().toString();
//    }
//
//    @Override
//    public void execute() {
//        if (a==value){
//            gameId = UUID.randomUUID().toString();
//            a=0;
//        }
//        a++;
//        return "Game_" + gameId;
//    }
//}
//
//
