package Minesweeper;

import java.util.ArrayList;
import java.util.Collections;

public class test {
    public static void main(String[] args) {
        String[] test = Minesweeper.generateBoardState(20, 20, 120);

        ArrayList<Tile> neighbours = Minesweeper.neighbours(test, "01", "20");

        for (Tile x:neighbours) {
            System.out.println(x);
        }
    }
}
