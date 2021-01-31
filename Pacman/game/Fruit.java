package Pacman.game;

import javafx.scene.paint.Color;

public class Fruit extends AbstractStaticMapElement {

    Fruit(Vector2d position, int roundNumber) {
        super(position, roundNumber * 5, Color.RED);
    }


}
