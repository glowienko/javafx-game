package com.game.space.invaders;

import com.game.space.invaders.GameApplication.StageReadyEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MainStageInitializer implements ApplicationListener<StageReadyEvent> {
    @Value("main-game.fxml")
    private ClassPathResource gameResource;
    private String mainWindowTitle;
    private double windowHeight;
    private double windowWidth;

    public MainStageInitializer(@Value("${game.title}") String mainWindowTitle,
                                @Value("${game.height}") double windowHeight,
                                @Value("${game.width}") double windowWidth) {
        this.mainWindowTitle = mainWindowTitle;
        this.windowHeight = windowHeight;
        this.windowWidth = windowWidth;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        Stage gameStage = event.getStage();

        try {
            setupMainGameWindow(gameStage);
            gameStage.show();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private void setupMainGameWindow(Stage gameStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(gameResource.getURL());
        Parent parent = fxmlLoader.load();

        gameStage.setScene(new Scene(parent, windowWidth, windowHeight));
        gameStage.setTitle(mainWindowTitle);

        printProperties();
    }

    private void printProperties() {
        System.out.println("title: " + mainWindowTitle);
        System.out.println("wid: " + windowWidth);
        System.out.println("hei: " + windowHeight);
    }
}
