package game;

public class Player extends AbstractDynamicMapElement {

    Player(Vector2d position, Map map, Direction direction) {
        super(position, map, direction);
    }


    @Override
    public String toString() {
        return "p";
    }
}
