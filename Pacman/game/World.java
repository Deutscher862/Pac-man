package Pacman.game;

import javafx.application.Application;
import javafx.stage.Stage;

public class World extends Application {
    public static void main(String[] args){ launch(args); }

    @Override
    public void start(Stage stage) throws Exception {
        Engine gameEngine = new Engine(stage);
        gameEngine.run();
    }
}
