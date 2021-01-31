package Pacman.game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Coin extends AbstractStaticMapElement {

    Coin(Vector2d position, int roundNumber) {
        super(position, roundNumber, new Circle(5, Color.WHITE));
    }

    @Override
    public String toString() {
        return "o";
    }

}