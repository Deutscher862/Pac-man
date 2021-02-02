package Pacman.game;

public class Fruit extends AbstractStaticMapElement {

    Fruit(Vector2d position, int roundNumber) {
        super(position, roundNumber * 5, "resources/fruit.png");
    }
}
