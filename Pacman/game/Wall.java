package Pacman.game;

import Pacman.visualizer.Tile;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Wall extends AbstractStaticMapElement {
    Wall(Vector2d position) {
        super(position, 0, new Rectangle(20, 20, Color.BLUE));
    }

    @Override
    public String toString() {
        return "w";
    }
}