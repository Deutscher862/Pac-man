package Pacman.visualizer;

import Pacman.game.AbstractMapElement;
import Pacman.game.Vector2d;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Tile extends StackPane {
    //pojedyncza płytka reprezentująca pojedyncze pole na mapie
    private final int size;
    private Shape shape;

    public Tile(int size,Vector2d position, AbstractMapElement object){
        this.size = size;
        setContent(object);
        this.getChildren().add(this.shape);
        this.setTranslateX(position.x*size+10);
        this.setTranslateY(position.y*size+10);
    }

    public void setContent(AbstractMapElement object) {
        if(object != null){
            this.shape = object.getShape();
        }
        else {
            this.shape = new Rectangle(size, size);
            this.shape.setFill(Color.BLACK);
        }
        //this.getChildren().set(0, this.shape);
    }
}
