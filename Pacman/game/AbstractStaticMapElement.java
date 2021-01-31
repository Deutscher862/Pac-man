package Pacman.game;

import javafx.scene.shape.Shape;

abstract class AbstractStaticMapElement extends AbstractMapElement{
    private final int value;

    public AbstractStaticMapElement(Vector2d position, int value, Shape shape) {
        super(position, shape);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
