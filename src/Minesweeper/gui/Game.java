package Minesweeper.gui;

import Minesweeper.Minesweeper;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Game extends Application {
    private static final int introWidth = 900;
    private static final int introHeight = 900;
    private static final String URI_BASE = "assets/";

    private final Group introRoot = new Group();
    private final Group introControls = new Group();
    private final Group introBackground = new Group();

    private final Group gameRoot = new Group();
    private final Group gameHint = new Group();
    private final Group gameBoard = new Group();


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Minesweeper");

        introRoot.getChildren().add(introControls);
        introRoot.getChildren().add(introBackground);

        makeIntroControls();
        makeIntroBackground();

        primaryStage.setScene(new Scene(introRoot, introHeight, introHeight));

        primaryStage.show();
    }

    private void makeIntroBackground() {
        introBackground.getChildren().clear();
        ImageView baseBoard = new ImageView();
        Image background = new Image(Game.class.getResource(URI_BASE + "background.png").toString());

        Text text = new Text();
        text.setText("Minesweeper");
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 60));
        text.setFill(Color.WHITE);
        text.setLayoutX(230);
        text.setLayoutY(150);

        baseBoard.setImage(background);
        baseBoard.setFitWidth(introWidth);
        baseBoard.setFitHeight(introHeight);

        introBackground.getChildren().add(baseBoard);
        introBackground.getChildren().add(text);
        introBackground.toBack();
    }

    private void makeIntroControls() {

    }
}
