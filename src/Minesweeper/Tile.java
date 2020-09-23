package Minesweeper;

import java.util.ArrayList;
import java.util.Random;

public class Tile {
    private final String type;
    private final int x;
    private final int y;

    // Here x and y are coordinate, and don't include 0 for both x and y
    Tile(String placement) {
        this.type = placement.substring(0,1);
        this.x = Integer.parseInt(placement.substring(1,2));
        this.y = Integer.parseInt(placement.substring(2,3));
    }

    public String getType() {
        return this.type;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public static ArrayList<Tile> deserialize(String state) {
        ArrayList<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < state.length(); i+=3) {
            tiles.add(new Tile(state.substring(i, i+3)));
        }
        return tiles;
    }

    public static String serialize(ArrayList<Tile> tiles) {
        StringBuilder acc = new StringBuilder();
        for (Tile x:tiles) {
            acc.append(x.toString());
        }
        return acc.toString();
    }

    public static String[] bombGenerator(String[] state) {
        int boundX = Integer.parseInt(state[0]);
        int boundY = Integer.parseInt(state[1]);
        Random rand = new Random(System.nanoTime());
        return state; //FIXME: Not finished here
    }

    @Override
    public String toString() {
        return type + x + y;
    }
}
