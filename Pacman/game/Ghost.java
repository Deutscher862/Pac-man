package Pacman.game;

import javafx.scene.paint.Color;

import java.util.Random;

public class Ghost extends AbstractDynamicMapElement {
    private final static Random rand = new Random();

    Ghost(Vector2d position, Map map, Direction direction) {
        super(position, map, direction, Color.MAGENTA);
    }

    @Override
    public void move(){
        int turnNumber = rand.nextInt(4);
        for(int i = 0 ; i < turnNumber; i++){
            this.direction = this.direction.next();
        }
        super.move();
    }

    @Override
    public String toString() {
        return "g";
    }
}
