package game;

public class Coin extends AbstractStaticMapElement {

    Coin(Vector2d position) {
        super(position);
    }

    @Override
    public String toString() {
        return "o";
    }
}