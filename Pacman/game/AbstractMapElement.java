package Pacman.game;

import javafx.scene.image.Image;

public abstract class AbstractMapElement{
    protected Vector2d position;
    private Image image;

    public AbstractMapElement(Vector2d position, String path){
        this.position = position;
        this.image = new Image(path, 20, 20, false, false);
    }

    public Vector2d getPosition() {
        return position;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public Image getImage(){return this.image;}

    public void setImage(Image image){
        this.image = image;
    }
}