package Pacman.game;

import javafx.scene.shape.Shape;

public abstract class AbstractMapElement{
    protected Vector2d position;
    private final Shape shape;

    public AbstractMapElement(Vector2d position, Shape shape){
        this.position = position;
        this.shape = shape;
    }

    public Vector2d getPosition() {
        return position;
    }

    public Shape getShape() {
        return shape;
    }

}