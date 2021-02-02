package Pacman.game;

import javafx.scene.image.ImageView;

public class Player extends AbstractDynamicMapElement {
    private boolean powerUp;
    private Direction imageDirection;
    private ImageView iv;

    Player(Vector2d position, Map map, Direction direction) {
        super(position, map, direction, "resources/pacman.png");
        this.powerUp = false;
        this.imageDirection = Direction.EAST;
    }

    public void setPowerUp(boolean powerUp) {
        this.powerUp = powerUp;
    }

    public boolean getPowerUp(){
        return this.powerUp;
    }

    public void rotateImage(){
        if(this.direction == null) return;
        while(this.imageDirection != this.direction){
            iv = new ImageView(this.getImage());
            iv.setRotate(90);
            this.imageDirection = this.imageDirection.next();
            this.setImage(iv.snapshot(null, null));
        }

    }

    @Override
    public String toString() {
        return "p";
    }

}
