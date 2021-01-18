package game;

public class Ghost extends AbstractDynamicMapElement {

    Ghost(Vector2d position, Map map, Direction direction) {
        super(position, map, direction);
    }

    @Override
    public String toString() {
        return "g";
    }
}
