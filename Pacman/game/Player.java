package Pacman.game;

import javafx.scene.paint.Color;

public class Player extends AbstractDynamicMapElement {

    Player(Vector2d position, Map map, Direction direction) {
        super(position, map, direction, Color.YELLOW);
    }

    @Override
    public String toString() {
        return "p";
    }
}
