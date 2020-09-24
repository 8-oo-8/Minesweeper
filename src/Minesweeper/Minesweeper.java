package Minesweeper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Minesweeper {
    // String[] -> [x-width][y-height][board state]
    // Maximum board 99 * 99 (can't be larger)
    public static boolean isBoardStateValid(String[] state) {
        if (state[2].length() % 5 != 0) return false;

        int boundX = Integer.parseInt(state[0]);
        int boundY = Integer.parseInt(state[1]);
        int counter = 0;
        ArrayList<String> posL = new ArrayList<>();
        HashSet<String> posS = new HashSet<>();
        String[] typeA = {"B", "F", "N"};
        List<String> types = Arrays.asList(typeA);

        ArrayList<Tile> tiles = Tile.deserialize(state[2]);
        for (Tile x:tiles) {
            if (!types.contains(x.getType()) || Integer.parseInt(x.getX()) < 1 ||
                    Integer.parseInt(x.getX()) > boundX ||
                    Integer.parseInt(x.getY()) < 1 || Integer.parseInt(x.getY()) > boundY) return false;
            posL.add(x.getX()+""+x.getY());
            posS.add(x.getX()+""+ x.getY());
            counter++;
        }
        if (posL.size() != posS.size()) return false;
        return counter == boundX * boundY;
    }

    // Generate a valid board state for the given width and height, with given number of bombs
    public static String[] generateBoardState(int width, int height, int bomb) {
        String[] rtn = {Integer.toString(width), Integer.toString(height), ""};
        for (int i = 0; i < bomb; i++) {
            Tile.bombGenerator(rtn);
        }

        Tile.fillInNormal(rtn);

        return rtn;
    }

    public static String[] updateBoardState(String[] state, String newTile) {
        String[] rtn = {state[0], state[1], ""};
        int boundX = Integer.parseInt(state[0]);
        int boundY = Integer.parseInt(state[1]);
        ArrayList<Tile> tiles = Tile.deserialize(state[2]);
        ArrayList<String> pos = new ArrayList<>();
        Tile newTileT = new Tile(newTile);
        String tilePos = newTileT.getX() + newTileT.getY();

        for (Tile x:tiles) {
            pos.add(x.getX() + x.getY());
        }

        if (pos.contains(tilePos)) {
            int index = pos.indexOf(tilePos);
            tiles.set(index, newTileT);
        } else {
            int x = Integer.parseInt(newTileT.getX());
            int y = Integer.parseInt(newTileT.getY());
            if (x > 0 && x <= boundX && y > 0 && y <= boundY) {
                tiles.add(newTileT);
            }
        }

        rtn[2] = Tile.serialize(tiles);

        return rtn;
    }

}
