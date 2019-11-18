package com.game.space.invaders;

import com.game.space.invaders.GameApplication.StageReadyEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MainStageInitializer implements ApplicationListener<StageReadyEvent> {
    @Value("main-game.fxml")
    private ClassPathResource gameResource;
    private ApplicationContext context;
    private String mainWindowTitle;
    private double windowHeight;
    private double windowWidth;

    public MainStageInitializer(ApplicationContext context,
                                @Value("${game.title}") String mainWindowTitle,
                                @Value("${game.height}") double windowHeight,
                                @Value("${game.width}") double windowWidth) {
        this.context = context;
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
        //needed for applicationContext beans usage in javaFX wiring - take bean of aClassType from spring context
        fxmlLoader.setControllerFactory(aClassType -> context.getBean(aClassType));
        Parent parent = fxmlLoader.load();

        gameStage.setScene(new Scene(parent, windowWidth, windowHeight));
        gameStage.setTitle(mainWindowTitle);
    }
}
