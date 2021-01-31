package Pacman.game;

import javafx.scene.paint.Color;

public class Wall extends AbstractStaticMapElement {
    Wall(Vector2d position) {
        super(position, 0,Color.BLUE);
    }

    @Override
    public String toString() {
        return "w";
    }
}