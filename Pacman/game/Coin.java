package Pacman.game;

public class Coin extends AbstractStaticMapElement {

    Coin(Vector2d position, int roundNumber) {
        super(position, roundNumber, "resources/coin.png");
    }

    @Override
    public String toString() {
        return "o";
    }

}