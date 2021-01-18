package game;

public class Vector2d {
    public final int x;
    public final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return ("(" + this.x +  ", " + this.y + ")");
    }

    public Vector2d Add(Vector2d other){
        return new Vector2d(this.x + other.x, this.y + other.y);
    }
}