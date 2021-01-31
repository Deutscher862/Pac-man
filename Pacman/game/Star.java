package Pacman.game;

import javafx.scene.paint.Color;

public class Star extends AbstractStaticMapElement {

    Star(Vector2d position, int roundNumber) {
        super(position, roundNumber * 10,Color.GOLD);
    }

    @Override
    public String toString() {
        return "*";
    }

}