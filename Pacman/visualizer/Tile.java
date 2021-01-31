package Pacman.visualizer;

import Pacman.game.AbstractMapElement;
import Pacman.game.Vector2d;
import Pacman.game.Wall;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Tile extends StackPane {
    //pojedyncza płytka reprezentująca pojedyncze pole na mapie
    private final int size;
    private Shape shape;

    public Tile(int size, Vector2d position, AbstractMapElement object) {
        this.size = size;
        if(object instanceof Wall) this.shape = new Rectangle(size, size);
        else this.shape = new Circle(10);
        this.getChildren().add(this.shape);
        setContent(object);
        this.setTranslateX(position.x * size + 10);
        this.setTranslateY(position.y * size + 10);
    }

    public void setContent(AbstractMapElement object) {
        if (object != null) {
            this.shape.setFill(object.getColor());
        } else {
            this.shape.setFill(Color.BLACK);
        }
    }
}