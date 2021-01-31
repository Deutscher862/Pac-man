package Pacman.game;

import javafx.scene.paint.Color;

public class Player extends AbstractDynamicMapElement {
    private boolean powerUp;

    Player(Vector2d position, Map map, Direction direction) {
        super(position, map, direction, Color.YELLOW);
        this.powerUp = false;
    }

    public void setPowerUp(boolean powerUp) {
        this.powerUp = powerUp;
    }

    public boolean getPowerUp(){
        return this.powerUp;
    }

    @Override
    public String toString() {
        return "p";
    }

}
