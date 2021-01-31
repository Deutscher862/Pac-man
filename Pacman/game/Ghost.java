package Pacman.game;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class Ghost extends AbstractDynamicMapElement {
    private final static Random rand = new Random();

    Ghost(Vector2d position, Map map, Direction direction) {
        super(position, map, direction, Color.MAGENTA);
    }

    @Override
    public void move(){
        //szukam w które strony może poruszyć się duch, nastęnie losuję jedną z nich
        Direction testDirection = this.direction;
        ArrayList<Direction> possibleDirections = new ArrayList<>();
        for(int i =0; i < 4; i++){
            Vector2d possiblePosition = this.position.Add(testDirection.toUnitVector());
            if(possiblePosition.x == -1) possiblePosition = new Vector2d(27, possiblePosition.y);
            else if(possiblePosition.x == 28) possiblePosition = new Vector2d(0, possiblePosition.y);
            if(map.canMoveTo(possiblePosition))
                possibleDirections.add(testDirection);
            testDirection = testDirection.next();
        }
        int turnNumber = rand.nextInt(possibleDirections.size());
        this.direction = possibleDirections.get(turnNumber);
        super.move();
    }

    @Override
    public String toString() {
        return "g";
    }
}