package Minesweeper;

import java.util.ArrayList;
import java.util.Collections;

public class test {
    public static void main(String[] args) {
        String[] test = Minesweeper.generateBoardState(20, 20, 397);

        ArrayList<Tile> tiles = Tile.deserialize(test[2]);

        Collections.sort(tiles);

        System.out.println(Tile.serialize(tiles));

        test[2] = Tile.serialize(tiles);

        System.out.println(Minesweeper.isBoardStateValid(test));
    }
}
