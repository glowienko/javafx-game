package com.game.space.invaders;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SpringApplication {

    public static void main(String[] args) {
        Application.launch(GameApplication.class, args);
    }
}
