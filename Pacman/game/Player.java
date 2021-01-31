package Pacman.game;

import Pacman.visualizer.Tile;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Player extends AbstractDynamicMapElement {

    Player(Vector2d position, Map map, Direction direction) {
        super(position, map, direction, Color.YELLOW);
    }

    @Override
    public String toString() {
        return "p";
    }

}
