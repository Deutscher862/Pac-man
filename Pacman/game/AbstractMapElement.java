package Pacman.game;

import javafx.scene.paint.Color;

public abstract class AbstractMapElement{
    protected Vector2d position;
    private final Color color;

    public AbstractMapElement(Vector2d position, Color color){
        this.position = position;
        this.color = color;
    }

    public Vector2d getPosition() {
        return position;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public Color getColor(){return this.color;}
}