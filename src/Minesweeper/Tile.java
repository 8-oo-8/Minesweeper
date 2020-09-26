package Minesweeper;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Tile implements Comparable<Tile> {
    private final String type;
    private final String x;
    private final String y;
    private final String placement;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tile)) return false;
        Tile tile = (Tile) o;
        return Objects.equals(type, tile.type) && Objects.equals(x, tile.x) && Objects.equals(y, tile.y) && Objects.equals(placement, tile.placement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, x, y, placement);
    }

    // Here x and y are coordinate, and don't include 0 for both x and y
    public Tile(String placement) {
        this.placement = placement;
        this.type = placement.substring(0,1);
        this.x = placement.substring(1,3);
        this.y = placement.substring(3,5);
    }

    public String getType() {
        return this.type;
    }

    public String getX() {
        return this.x;
    }

    public String getY() {
        return this.y;
    }

    public static ArrayList<Tile> deserialize(String state) {
        ArrayList<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < state.length(); i+=5) {
            tiles.add(new Tile(state.substring(i, i+5)));
        }
        return tiles;
    }
    public static ArrayList<String> piecesList(String placement) {
        ArrayList<String> list = new ArrayList<>();
        if (placement.length() % 5 != 0) {
            return null;
        } else {
            for (int i = 0; i < placement.length(); i += 5) {
                String sub = placement.substring(i, i + 5);
                list.add(sub);
            }
        }
        return list;
    }
    public static String serialize(ArrayList<Tile> tiles) {
        StringBuilder acc = new StringBuilder();
        for (Tile x:tiles) {
            acc.append(x.toString());
        }
        return acc.toString();
    }

    // Add one bomb to the bomb
    public static void bombGenerator(String[] state) {
        ArrayList<Tile> tiles = deserialize(state[2]);
        ArrayList<String> pos = new ArrayList<>();

        int boundX = Integer.parseInt(state[0]);
        int boundY = Integer.parseInt(state[1]);
        int newX;
        int newY;
        String newXstr;
        String newYstr;

        for (Tile x:tiles) {
            pos.add(x.getX()+""+x.getY());
        }

        Random rand = new Random(System.nanoTime());
        do {
            newX = rand.nextInt(boundX) + 1;
            newY = rand.nextInt(boundY) + 1;
            newXstr = (newX < 10) ? "0"+newX : newX+"";
            newYstr = (newY < 10) ? "0"+newY : newY+"";
        } while (pos.contains(newXstr + newYstr));

        String rtn = "B" + newXstr + newYstr;
        state[2] += rtn;

    }

    // Fill the empty place with normal tile
    public static void fillInNormal(String[] state) {
        ArrayList<Tile> tiles = deserialize(state[2]);
        ArrayList<String> pos = new ArrayList<>();

        int boundX = Integer.parseInt(state[0]);
        int boundY = Integer.parseInt(state[1]);

        for (Tile x:tiles) {
            pos.add(x.getX()+""+x.getY());
        }

        for (int i = 1; i <= boundX; i++) {
            for (int j = 1; j <= boundY; j++) {
                String placement = ((i < 10) ? "0"+i : i)+""+((j < 10) ? "0"+j : j);
                if (!pos.contains(placement)) {
                    state[2] += "N"+placement;
                }
            }
        }

    }

    public static Tile getTileFromXY(String[] state, String xs, String ys) {
        int x = Integer.parseInt(xs);
        int y = Integer.parseInt(ys);
        int boundX = Integer.parseInt(state[0]);
        int boundY = Integer.parseInt(state[1]);
        ArrayList<Tile> tiles = Tile.deserialize(state[2]);

        if (x > boundX || x <= 0 || y > boundY || y <= 0) {
            return null;
        } else {
            for (Tile t:tiles) {
                if ((t.getX()+t.getY()).equals(xs+ys)) {
                    return t;
                }
            }
        }
        return null;
    }

    public static Tile getExactTile(ArrayList<Tile> tiles, String xs, String ys) {
        for (Tile x:tiles) {
            if ((xs+ys).equals(x.getX()+x.getY())) {
                return x;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.placement;
    }

    @Override
    public int compareTo(Tile o) {
        int thisX = Integer.parseInt(this.getX());
        int thisY = Integer.parseInt(this.getY());
        int otherX = Integer.parseInt(o.getX());
        int otherY = Integer.parseInt(o.getY());

        if (thisX != otherX) {
            return Integer.compare(thisX, otherX);
        } else {
            return Integer.compare(thisY, otherY);
        }
    }
}
