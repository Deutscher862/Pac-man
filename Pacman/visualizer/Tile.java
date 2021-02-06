package Pacman.visualizer;

import Pacman.game.AbstractMapElement;
import Pacman.game.Vector2d;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

public class Tile extends StackPane {
    private static int size;
    private static Canvas canvas;
    //pojedyncza płytka reprezentująca pojedyncze pole na mapie
    private final GraphicsContext border;
    private final Vector2d position;

    public Tile(Canvas canvas, int size, Vector2d position, AbstractMapElement object) {
        Tile.canvas = canvas;
        Tile.size = size;
        this.position = position;
        this.getChildren().add(Tile.canvas);
        this.border = Tile.canvas.getGraphicsContext2D();
        setImage(object);
    }

    public void setImage(AbstractMapElement object){
        Image objectImage;
        if(object == null) objectImage = new Image("resources/null.png");
        else objectImage = object.getImage();
        this.border.drawImage(objectImage, this.position.x * Tile.size, this.position.y*Tile.size);
    }
}