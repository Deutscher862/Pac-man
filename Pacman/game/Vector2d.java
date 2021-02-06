package Pacman.game;

import java.util.Objects;
import java.util.Random;

public class Vector2d {
    private static final Random rand = new Random();
    public final int x;
    public final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Direction getDirectionTowardsVector(Vector2d endPosition){
        if(this.x != endPosition.x ){
            if(this.x > endPosition.x) return Direction.WEST;
            else return Direction.EAST;
        }
        else if(this.y != endPosition.y){
            if(this.y > endPosition.y) return Direction.NORTH;
            else return Direction.SOUTH;
        }
        else return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2d vector2d = (Vector2d) o;
        return x == vector2d.x && y == vector2d.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return ("(" + this.x +  ", " + this.y + ")");
    }

    public Vector2d add(Vector2d other){
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public Vector2d getRandomPosition(){
        return new Vector2d(rand.nextInt(this.x), rand.nextInt(this.y));
    }
}