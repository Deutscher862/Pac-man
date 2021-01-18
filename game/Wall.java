package game;

public class Wall extends AbstractStaticMapElement {
    Wall(Vector2d position) {
        super(position);
    }

    @Override
    public String toString() {
        return "w";
    }
}