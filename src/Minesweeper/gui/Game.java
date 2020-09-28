package Minesweeper.gui;

import Minesweeper.Minesweeper;
import Minesweeper.Tile;
import Minesweeper.Hint;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashSet;


public class Game extends Application {
    private static final int introWidth = 1024;
    private static final int introHeight = 768;
    private static final int tileSize = 30;
    private static final String URI_BASE = "assets/";
    AudioClip loop;
    private final Group introRoot = new Group();
    private final Group introControls = new Group();
    private final Group introBackground = new Group();
    private final Group introInstruction = new Group();
    private TextField widthTextField;
    private TextField heightTextField;
    private TextField bombNumber;
    private boolean problemNumber = false;

    private final Group gameRoot = new Group();
    private final Group gameHint = new Group();
    private final Group gameBoard = new Group();
    private final StackPane gameInstruction = new StackPane();
    private boolean isGameFinished = false;
    private boolean isGameSuccess = false;
    private int normalTileNum;
    private String[] state;
    private ArrayList<Tile> tiles;
    private ArrayList<TileGUI> showTile;
    private ArrayList<Hint> hints;
    private final HashSet<Tile> clickedNormal = new HashSet<>();
    private final ArrayList<Tile> original = new ArrayList<>();


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Minesweeper");

        introRoot.getChildren().add(introControls);
        introRoot.getChildren().add(introBackground);
        introRoot.getChildren().add(introInstruction);

        makeIntroBackground();
        makeIntroControls();
        makeIntroInstruction();

        primaryStage.setScene(new Scene(introRoot, introWidth, introHeight));

        primaryStage.show();
    }

    private void makeIntroBackground() {
        introBackground.getChildren().clear();
        ImageView baseBoard = new ImageView();
        Image background = new Image(Game.class.getResource(URI_BASE + "background.png").toString());

        baseBoard.setImage(background);
        baseBoard.setFitWidth(introWidth);
        baseBoard.setFitHeight(introHeight);

        introBackground.getChildren().add(baseBoard);
        introBackground.toBack();
    }

    private void makeIntroInstruction() {
        introInstruction.getChildren().clear();

        Text t = new Text();
        t.setFill(Color.RED);
        t.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        if (!problemNumber) {
            t.setText("Please enter game board width, height and number of bombs!");
            t.setLayoutX(160);
        } else {
            t.setText("The number you have entered is not accepted! Please re-enter the valid ones!");
            t.setLayoutX(75);
        }
        t.setLayoutY(215);

        introInstruction.getChildren().add(t);
    }

    private void makeIntroControls() {
        // Set style of title
        Text introTitle = new Text();
        introControls.getChildren().add(introTitle);

        introTitle.setText("Minesweepers");
        introTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 92));
        introTitle.setWrappingWidth(introWidth - 270);
        introTitle.setLayoutX((float) introWidth / 2 - introTitle.getWrappingWidth() / 2);
        introTitle.setLayoutY(140);
        introTitle.setFill(Color.WHITE);

        // Enter game data
        Label width = new Label("Width:");
        width.setTextFill(Color.WHITE);
        width.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        widthTextField = new TextField();
        widthTextField.setPrefWidth(100);
        introControls.getChildren().add(width);

        Label height = new Label("Height:");
        height.setTextFill(Color.WHITE);
        height.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        heightTextField = new TextField();
        heightTextField.setPrefWidth(100);
        introControls.getChildren().add(height);

        Label bomb = new Label("Number of bombs");
        bomb.setTextFill(Color.WHITE);
        bomb.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        bombNumber = new TextField();
        bombNumber.setPrefWidth(100);
        introControls.getChildren().add(bomb);

        HBox hb = new HBox();
        hb.getChildren().addAll(width, widthTextField, height, heightTextField, bomb, bombNumber);
        hb.setSpacing(30);
        hb.setLayoutX(120);
        hb.setLayoutY(introHeight - 220);
        introControls.getChildren().add(hb);

        // Set style of start button
        Button startButton = new Button("START");
        introControls.getChildren().add(startButton);
        startButton.setPrefWidth(350);
        startButton.setPrefHeight(100);
        startButton.setFont(Font.font("Verdana", FontWeight.BOLD, 60));
        startButton.setStyle("-fx-background-color: #55cc55; " +
                "-fx-border-color: #000000; " +
                "-fx-border-width: 5px;");
        startButton.setLayoutX((float) introWidth / 2 - startButton.getPrefWidth() / 2);
        startButton.setLayoutY(introHeight - startButton.getPrefHeight() - 50);

        // Set on button action
        startButton.setOnAction(actionEvent -> {
            try {
                int gameWidth = Integer.parseInt(widthTextField.getText());
                int gameHeight = Integer.parseInt(heightTextField.getText());
                int gameBomb = Integer.parseInt(bombNumber.getText());

                if (gameBomb > 0 && gameBomb < gameWidth * gameHeight && gameWidth > 0 && gameWidth < 100 &&
                        gameHeight > 0 && gameHeight < 100 && (gameWidth != 1 || gameHeight != 1)) {

                    Scene gameScene = new Scene(gameRoot, tileSize * gameWidth, tileSize * gameHeight);

                    state = Minesweeper.generateBoardState(gameWidth, gameHeight, gameBomb);
                    hints = Minesweeper.generateHint(state);
                    tiles = Tile.deserialize(state[2]);
                    showTile = drawTiles();
                    normalTileNum = gameWidth * gameHeight - gameBomb;


                    gameRoot.getChildren().add(gameHint);
                    gameRoot.getChildren().add(gameBoard);
                    gameRoot.getChildren().add(gameInstruction);

                    makeHinter();
                    makeBoard();

                    Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                    window.setScene(gameScene);
                    window.show();

                } else {
                    problemNumber = true;
                    makeIntroInstruction();
                }
            } catch (Exception ignored) {
                problemNumber = true;
                makeIntroInstruction();
            }
        });
    }

    private void isGameSuccess() {
        if (Tile.isNoBomb(tiles)) {
            if (normalTileNum == clickedNormal.size()) {
                isGameFinished = true;
                isGameSuccess = true;
            }
        }
    }

    private void checkGameFinished() {
        if (isGameFinished) {
            makeInstruction();
        }
    }

    private void makeInstruction() {
        gameInstruction.getChildren().clear();

        Text t = new Text();
        t.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        Image image = new Image(Game.class.getResource(URI_BASE + "success.png").toString());
        if (isGameFinished) {
            if (isGameSuccess) {
                t.setText("bomb has been defuse");
                t.setFill(Color.GREEN);
                ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(150);
                    imageView.setFitHeight(150);

                loop = new AudioClip(Game.class.getResource(URI_BASE + 2 + ".wav").toString());
                loop.play();
                gameInstruction.getChildren().add(imageView);
            } else {
                t.setText("Terrorist win");
                t.setFill(Color.RED);
            }
        }
        gameInstruction.getChildren().add(t);
    }

    private void makeBoard() {
        gameBoard.getChildren().clear();

        gameBoard.getChildren().addAll(showTile);
    }

    private ArrayList<TileGUI> drawTiles() {
        ArrayList<TileGUI> rtn = new ArrayList<>();
        for (Tile x : tiles) {
            rtn.add(new TileGUI(x.toString() + "N"));
        }
        return rtn;
    }

    // Make the hinter layer
    private void makeHinter() {
        ArrayList<HintGUI> showHint = drawHints();

        gameHint.getChildren().addAll(showHint);
        gameHint.toBack();
    }

    // Transform from the game state to the ArrayList of visible hints
    private ArrayList<HintGUI> drawHints() {
        ArrayList<HintGUI> rtn = new ArrayList<>();
        for (Hint x : hints) {
            rtn.add(new HintGUI(x.getValue(), Integer.parseInt(x.getX()), Integer.parseInt(x.getY())));
        }
        return rtn;
    }

    // TileGUI inner class
    class TileGUI extends ImageView {
        String type;
        int x;
        int y;
        String showType;
        String placement;

        TileGUI(String placement) {
            type = placement.substring(0, 1);
            x = Integer.parseInt(placement.substring(1, 3));
            y = Integer.parseInt(placement.substring(3, 5));
            showType = placement.substring(5, 6);
            this.placement = placement;

            // Load the Normal image
            Image image;
            image = new Image(TileGUI.class.getResource(URI_BASE + showType +".png").toString());
            setImage(image);

            // Put the image to the correct place
            setFitWidth(tileSize);
            setFitHeight(tileSize);
            setLayoutX(tileSize * (x - 1));
            setLayoutY(tileSize * (y - 1));

            // Set on event handler
            setOnMousePressed(event -> {
                if (!isGameFinished) {
                    String xs = (x < 10) ? "0" + x : x + "";
                    String ys = (y < 10) ? "0" + y : y + "";
                    String central = this.placement.substring(0, 5);
                    if (type.equals("N")) {
                        if (event.getButton() == MouseButton.PRIMARY) {
                            tiles = Tile.deserialize(state[2]);
                            updateTileGUI(new TileGUI(central + "T"));
                            clickedNormal.add(new Tile(central));
                            scanSurroundings(central);
                            makeBoard();
                            isGameSuccess();
                            checkGameFinished();
                        } else if (event.getButton() == MouseButton.SECONDARY) {
                            original.add(new Tile(type + xs + ys));
                            Minesweeper.updateBoardState(state, "F" + xs + ys);
                            tiles = Tile.deserialize(state[2]);
                            updateTileGUI(new TileGUI("F" + xs + ys + "F"));
                            makeBoard();
                            isGameSuccess();
                            checkGameFinished();
                        }

                    } else if (type.equals("B")) {
                        if (event.getButton() == MouseButton.PRIMARY) {
                            isGameFinished = true;
                            tiles = Tile.deserialize(state[2]);
                            updateTileGUI(new TileGUI("B" + xs + ys + "B"));
                        } else if (event.getButton() == MouseButton.SECONDARY) {
                            original.add(new Tile(type + xs + ys));
                            Minesweeper.updateBoardState(state, "F" + xs + ys);
                            tiles = Tile.deserialize(state[2]);
                            updateTileGUI(new TileGUI("F" + xs + ys + "F"));
                        }
                        makeBoard();
                        isGameSuccess();
                        checkGameFinished();

                    } else {
                        Tile before = Tile.getExactTile(original, xs, ys);
                        if (event.getButton() == MouseButton.PRIMARY) {
                            if (before != null) {
                                if (before.getType().equals("B")) {
                                    isGameFinished = true;
                                    Minesweeper.updateBoardState(state, "B" + xs + ys);
                                    tiles = Tile.deserialize(state[2]);
                                    updateTileGUI(new TileGUI("B" + xs + ys + "B"));
                                } else {
                                    Minesweeper.updateBoardState(state, "N" + xs + ys);
                                    tiles = Tile.deserialize(state[2]);
                                    updateTileGUI(new TileGUI("N" + xs + ys + "T"));
                                    clickedNormal.add(new Tile("N" + xs + ys));
                                    scanSurroundings("N" + central.substring(1, 5));
                                }
                                makeBoard();
                                isGameSuccess();
                                checkGameFinished();
                            }
                        } else if (event.getButton() == MouseButton.SECONDARY) {
                            if (before != null) {
                                Minesweeper.updateBoardState(state, before.getType() + xs + ys);
                                tiles = Tile.deserialize(state[2]);
                                updateTileGUI(new TileGUI(before.getType() + xs + ys + "N"));
                                makeBoard();
                                isGameSuccess();
                                checkGameFinished();
                            }
                        }
                        original.remove(before);
                    }
                }
            });
        }

        int getWidth() { return this.x; }

        int getHeight() { return this.y; }

        void updateTileGUI(TileGUI newTileGUI) {
            for (int i = 0; i < showTile.size(); i++) {
                if ((showTile.get(i).getWidth() + "" + showTile.get(i).getHeight()).equals(
                        newTileGUI.getWidth() + "" + newTileGUI.getHeight())) {
                    showTile.remove(showTile.get(i));
                    showTile.add(newTileGUI);
                }
            }
        }

        void scanSurroundings(String central) {
            // Get the index of the instance Tile
            int i = tiles.indexOf( new Tile(central));
            if (i == -1) throw new IllegalArgumentException();
            // Get the hint below the instance Tile
            Hint hint = hints.get(i);
            // Get the hint below the instance Tile
            int value = hint.getValue();
            // If it's 0, make the surroundings visible
            if (value==0) {
                ArrayList<Tile> neighbours = Minesweeper.neighbours(state, placement.substring(1, 3), placement.substring(3, 5));
                String serialize = Tile.serialize(neighbours);
                ArrayList<String> tiles = Tile.piecesList(serialize);
                if (tiles != null) {
                    for (String tile : tiles) {
                        updateTileGUI(new TileGUI(tile.substring(0, 5) + "T"));
                        clickedNormal.add(new Tile(tile));
                    }
                }
            }
        }

        @Override
        public String toString() {
            String xs = (x < 10) ? "0" + x : x + "";
            String ys = (y < 10) ? "0" + y : y + "";
            return this.type + xs + ys;
        }
    }

    // HintGUI inner class
    static class HintGUI extends ImageView {
        private final int value;
        private final int x;
        private final int y;

        HintGUI(int value, int x, int y) {
            this.value = value;
            this.x = x;
            this.y = y;

            Image image = new Image(Game.class.getResource(URI_BASE + value + ".png").toString());
            setImage(image);

            // Put the image to the correct place
            setFitWidth(tileSize);
            setFitHeight(tileSize);
            setLayoutX(tileSize * (x - 1));
            setLayoutY(tileSize * (y - 1));
        }

        @Override
        public String toString() {
            String xs = (x < 10) ? "0" + x : x + "";
            String ys = (y < 10) ? "0" + y : y + "";
            return value + xs + ys;
        }
    }
}