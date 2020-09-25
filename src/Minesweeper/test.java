package Minesweeper;

import java.util.ArrayList;

public class test {
    public static void main(String[] args) {
        String[] test = Minesweeper.generateBoardState(3, 3, 1);

        Minesweeper.updateBoardState(test, "F0102");

        for (String x:
             test) {
            System.out.println(x);
        }
    }
}
