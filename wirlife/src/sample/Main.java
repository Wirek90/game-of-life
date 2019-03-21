package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.Controller.Game;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
            Game game = new Game();
            game.start(stage);


    }
    public static void main(String args[]){
        launch(args);

    }
}

