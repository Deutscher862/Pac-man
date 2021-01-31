package Pacman.game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Fruit extends AbstractStaticMapElement {

    Fruit(Vector2d position, int roundNumber) {
        super(position, roundNumber * 5, new Circle(5, Color.RED));
    }
}
