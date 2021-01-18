package game;

public class Star extends AbstractStaticMapElement {

    Star(Vector2d position) {
        super(position);
    }

    @Override
    public String toString() {
        return "*";
    }
}