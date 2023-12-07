package me.kangmin.swingy.entity.base;

public class Coordinate {
    private int x;
    private int y;

    public void moveBy(int deltaX, int deltaY) {
        this.x += deltaX;
        this.y += deltaY;
    }

    // ========== constructor ==========
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // ========== getter && setter ==========
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;

        if (x != that.x) return false;
        return y == that.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
