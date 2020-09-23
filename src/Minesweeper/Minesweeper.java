package Minesweeper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Minesweeper {
    // TODO: Some game related method here [Needless to write tests]
    // TODO: Create other classes for the game structure


    // String[] -> [x-width][y-height][board state]
    public static boolean isBoardStateValid(String[] state) {
        if (state[2].length() % 3 != 0) return false;

        int boundX = Integer.parseInt(state[0]);
        int boundY = Integer.parseInt(state[1]);
        int counter = 0;
        ArrayList<String> posL = new ArrayList<>();
        HashSet<String> posS = new HashSet<>();
        String[] typeA = {"B", "F", "N"};
        List<String> types = Arrays.asList(typeA);

        ArrayList<Tile> tiles = Tile.deserialize(state[2]);
        for (Tile x:tiles) {
            if (!types.contains(x.getType()) || x.getX() < 1 ||
                    x.getX() > boundX || x.getY() < 1 || x.getY() > boundY) return false;
            posL.add(x.getX()+""+x.getY());
            posS.add(x.getX()+""+ x.getY());
            counter++;
        }
        if (posL.size() != posS.size()) return false;
        return counter == boundX * boundY;
    }


}
