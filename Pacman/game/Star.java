package Pacman.game;

public class Star extends AbstractStaticMapElement {

    Star(Vector2d position, int roundNumber) {
        super(position, roundNumber * 10);
    }

    @Override
    public String toString() {
        return "*";
    }
}