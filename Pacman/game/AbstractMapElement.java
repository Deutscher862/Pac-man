package Pacman.game;

import javafx.scene.paint.Color;

public class AbstractMapElement {
    protected Vector2d position;
    private final Color color;

    public AbstractMapElement(Vector2d position, Color color){
        this.position = position;
        this.color = color;
    }

    public Vector2d getPosition() {
        return position;
    }

    public Color getColor() {
        return color;
    }
}