package Pacman.game;

public class Wall extends AbstractStaticMapElement {
    Wall(Vector2d position) {
        super(position, 0);
    }

    @Override
    public String toString() {
        return "w";
    }
}