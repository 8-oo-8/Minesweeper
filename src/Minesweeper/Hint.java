package Minesweeper;

public class Hint {
    private final int value;
    private final String x;
    private final String y;

    public Hint(int value, String x, String y) {
        this.value = value;
        this.x = x;
        this.y = y;
    }

    public int getValue() {
        return this.value;
    }

    public String getX() {
        return this.x;
    }

    public String getY() {
        return this.y;
    }

    @Override
    public String toString() {
        return value + x + y;
    }
}

