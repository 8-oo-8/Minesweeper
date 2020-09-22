package Minesweeper.gui;

import Minesweeper.Minesweeper;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Game extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO: Change below to the intro page and create gameplay page later
        Group root = new Group();
        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }
}
