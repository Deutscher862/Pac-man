package Pacman.game;

public class Coin extends AbstractStaticMapElement {

    Coin(Vector2d position, int roundNumber) {
        super(position, roundNumber);
    }

    @Override
    public String toString() {
        return "o";
    }

}