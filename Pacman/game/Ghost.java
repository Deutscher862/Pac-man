package Pacman.game;

import javafx.scene.image.Image;

import java.util.*;

public class Ghost extends AbstractDynamicMapElement {
    private final static Random rand = new Random();
    private static Image vulnerableImage;

    Ghost(Vector2d position, Map map, Direction direction, int number) {
        super(position, map, direction, "resources/ghost" + number + ".png");
        Ghost.vulnerableImage = new Image("resources/vulnerableGhost.png");
    }

    private void BFS(boolean[][] visited, Queue<Vector2d> positionsToVisit, Vector2d[][] lastPosition){
        while(!positionsToVisit.isEmpty()){
            //zdejmuję pozycję z kolejki
            Vector2d currentPosition = positionsToVisit.remove();
            visited[currentPosition.x][currentPosition.y] = true;

            Direction currentDirection = Direction.NORTH;
            Vector2d possiblePosition;
            //.out.println("Current" + currentPosition.toString());
            //sprawdzam wszystkie dostępne ruchy
            do{
                possiblePosition = currentPosition.add(currentDirection.toUnitVector());
                //System.out.println("Possible" + possiblePosition.toString());
                //jeśli mogę się przesunąć na daną pozycję to dodaję ją do kolejki
                if(possiblePosition.x >= 0 && possiblePosition.x < 28 && map.canMoveTo(possiblePosition) && !visited[possiblePosition.x][possiblePosition.y]){
                    lastPosition[possiblePosition.x][possiblePosition.y] = currentPosition;
                    positionsToVisit.add(possiblePosition);
                }
                currentDirection = currentDirection.next();
            }while(currentDirection != Direction.NORTH);
        }
    }

    private Vector2d getPositionToMove(Vector2d[][] lastPosition, Vector2d currentPosition){
        if(lastPosition[currentPosition.x][currentPosition.y].equals(this.position))
            return currentPosition;

            return getPositionToMove(lastPosition, lastPosition[currentPosition.x][currentPosition.y]);
    }

    @Override
    public void move(){

        boolean[][] visited = new boolean[28][32];
        for(int i = 0; i < 28; i++){
            for(int j = 0; j < 32; j++){
                visited[i][j] = false;
            }
        }
        Vector2d[][] lastPosition = new Vector2d[28][32];
        Queue<Vector2d> positionsToVisit = new LinkedList<>();
        positionsToVisit.add(this.position);
        BFS(visited, positionsToVisit, lastPosition);
        Vector2d newPosition = getPositionToMove(lastPosition, map.getPlayer().getPosition());
        this.direction = this.position.getDirectionTowardsVector(newPosition);


        super.move();
    }

    public static Image getVulnerableImage() {
        return vulnerableImage;
    }

    @Override
    public String toString() {
        return "g";
    }
}