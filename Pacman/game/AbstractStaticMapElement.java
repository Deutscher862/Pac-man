package Pacman.game;

import javafx.scene.paint.Color;

abstract class AbstractStaticMapElement extends AbstractMapElement{
    private final int value;

    public AbstractStaticMapElement(Vector2d position, int value, Color color) {
        super(position, color);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
