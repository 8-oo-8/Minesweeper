package Minesweeper;

import java.util.ArrayList;

public class test {
    public static void main(String[] args) {
        String[] test = Minesweeper.generateBoardState(99, 99, 4000);

        ArrayList<Tile> tiles = Tile.deserialize(test[2]);

        for (Tile x:tiles) {
            System.out.println(x);
        }

        ArrayList<Hint> hints = Minesweeper.generateHint(test);

        for (Hint x:hints) {
            System.out.println(x);
        }
    }
}
