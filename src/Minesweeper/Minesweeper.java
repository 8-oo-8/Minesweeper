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
        for (Tile x : tiles) {
            if (!types.contains(x.getType()) || Integer.parseInt(x.getX()) < 1 ||
                    Integer.parseInt(x.getX()) > boundX ||
                    Integer.parseInt(x.getY()) < 1 || Integer.parseInt(x.getY()) > boundY) return false;
            posL.add(x.getX() + "" + x.getY());
            posS.add(x.getX() + "" + x.getY());
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

        for (Tile x : tiles) {
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

    // TODO: Need refactor here
    public static ArrayList<Tile> neighbours(String[] boardState, String xs, String ys) {
        ArrayList<Tile> rtn = new ArrayList<>();
        int x = Integer.parseInt(xs);
        int y = Integer.parseInt(ys);
        int newX;
        int newY;
        String newXStr;
        String newYStr;
        int boundX = Integer.parseInt(boardState[0]);
        int boundY = Integer.parseInt(boardState[1]);

        if (x == 1 && y == 1) {
            rtn.add(Tile.getTileFromXY(boardState, "02", "01"));
            rtn.add(Tile.getTileFromXY(boardState, "01", "02"));
            rtn.add(Tile.getTileFromXY(boardState, "02", "02"));
        } else if (x == boundX && y == boundY) {
            newX = x - 1;
            newY = y - 1;
            newXStr = (newX < 10) ? "0" + newX : newX + "";
            newYStr = (newY < 10) ? "0" + newY : newY + "";
            rtn.add(Tile.getTileFromXY(boardState, newXStr, newYStr));
            rtn.add(Tile.getTileFromXY(boardState, newXStr, ys));
            rtn.add(Tile.getTileFromXY(boardState, xs, newYStr));
        } else if (x == boundX && y == 1) {
            newX = x - 1;
            newXStr = (newX < 10) ? "0" + newX : newX + "";
            rtn.add(Tile.getTileFromXY(boardState, newXStr, "01"));
            rtn.add(Tile.getTileFromXY(boardState, xs, "02"));
            rtn.add(Tile.getTileFromXY(boardState, newXStr, "02"));
        } else if (x == 1 && y == boundY) {
            newY = y - 1;
            newYStr = (newY < 10) ? "0" + newY : newY + "";
            rtn.add(Tile.getTileFromXY(boardState, "01", newYStr));
            rtn.add(Tile.getTileFromXY(boardState, "02", ys));
            rtn.add(Tile.getTileFromXY(boardState, "02", newYStr));
        } else if (x == 1 || x == boundX) {
            if (x == 1) {
                newY = y - 1;
                newYStr = (newY < 10) ? "0" + newY : newY + "";
                rtn.add(Tile.getTileFromXY(boardState, "01", newYStr));
                newY = y + 1;
                newYStr = (newY < 10) ? "0" + newY : newY + "";
                rtn.add(Tile.getTileFromXY(boardState, "01", newYStr));
                newXStr = "02";
                for (int i = -1; i < 2; i++) {
                    newY = y + i;
                    newYStr = (newY < 10) ? "0" + newY : newY + "";
                    rtn.add(Tile.getTileFromXY(boardState, newXStr, newYStr));
                }
            } else {
                newX = x - 1;
                newXStr = (newX < 10) ? "0" + newX : newX + "";
                newY = y - 1;
                newYStr = (newY < 10) ? "0" + newY : newY + "";
                rtn.add(Tile.getTileFromXY(boardState, xs, newYStr));
                newY = y + 1;
                newYStr = (newY < 10) ? "0" + newY : newY + "";
                rtn.add(Tile.getTileFromXY(boardState, xs, newYStr));
                for (int i = -1; i < 2; i++) {
                    newY = y + i;
                    newYStr = (newY < 10) ? "0" + newY : newY + "";
                    rtn.add(Tile.getTileFromXY(boardState, newXStr, newYStr));
                }
            }
        } else if (y == 1 || y == boundY) {
            if (y == 1) {
                newX = x - 1;
                newXStr = (newX < 10) ? "0" + newX : newX + "";
                rtn.add(Tile.getTileFromXY(boardState, newXStr, "01"));
                newX = x + 1;
                newXStr = (newX < 10) ? "0" + newX : newX + "";
                rtn.add(Tile.getTileFromXY(boardState, newXStr, "01"));
                newYStr = "02";
                for (int i = -1; i < 2; i++) {
                    newX = x + i;
                    newXStr = (newX < 10) ? "0" + newX : newX + "";
                    rtn.add(Tile.getTileFromXY(boardState, newXStr, newYStr));
                }
            } else {
                newX = x - 1;
                newXStr = (newX < 10) ? "0" + newX : newX + "";
                rtn.add(Tile.getTileFromXY(boardState, newXStr, ys));
                newX = x + 1;
                newXStr = (newX < 10) ? "0" + newX : newX + "";
                rtn.add(Tile.getTileFromXY(boardState, newXStr, ys));
                newY = y - 1;
                newYStr = (newY < 10) ? "0" + newY : newY + "";
                for (int i = -1; i < 2; i++) {
                    newX = x + i;
                    newXStr = (newX < 10) ? "0" + newX : newX + "";
                    rtn.add(Tile.getTileFromXY(boardState, newXStr, newYStr));
                }
            }
        } else {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    newX = x + i;
                    newY = y + j;

                    newXStr = (newX < 10) ? "0" + newX : newX + "";
                    newYStr = (newY < 10) ? "0" + newY : newY + "";

                    if (i != 0 || j != 0) {
                        rtn.add(Tile.getTileFromXY(boardState, newXStr, newYStr));
                    }
                }
            }
        }
        return rtn;
    }

    public static ArrayList<Hint> generateHint(String[] boardState) {
        ArrayList<Hint> rtn = new ArrayList<>();

        return rtn;
    }

}
