package Pacman.game;

import Pacman.visualizer.Tile;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Star extends AbstractStaticMapElement {

    Star(Vector2d position, int roundNumber) {
        super(position, roundNumber * 10, new Circle(10, Color.GOLD));
    }

    @Override
    public String toString() {
        return "*";
    }

}