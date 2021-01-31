package Pacman.game;

import javafx.scene.paint.Color;

public class Coin extends AbstractStaticMapElement {

    Coin(Vector2d position, int roundNumber) {
        super(position, roundNumber, Color.WHITE);
    }

    @Override
    public String toString() {
        return "o";
    }


}