package Pacman.game;

import javafx.scene.image.Image;

import java.util.*;

public class Ghost extends AbstractDynamicMapElement {
    private final Image initialImage;
    private static Image vulnerableImage;
    private static Random rand;

    Ghost(Vector2d position, Map map, Direction direction, int number) {
        super(position, map, direction, "resources/ghost" + number + ".png");
        this.initialImage = new Image("resources/ghost" + number + ".png");
        Ghost.vulnerableImage = new Image("resources/vulnerableGhost.png");
        Ghost.rand = new Random();
    }

    private void BFS(boolean[][] visited, Queue<Vector2d> positionsToVisit, Vector2d[][] lastPosition, Vector2d currentPlayerPosition){
        while(!positionsToVisit.isEmpty()){
            //zdejmuję pozycję z kolejki
            Vector2d currentPosition = positionsToVisit.remove();
            if(currentPosition.equals(currentPlayerPosition)) return;
            Direction currentDirection = Direction.NORTH;
            Vector2d possiblePosition;

            //sprawdzam wszystkie dostępne ruchy
            do{
                possiblePosition = currentPosition.add(currentDirection.toUnitVector());
                //jeśli mogę się przesunąć na daną pozycję to dodaję ją do kolejki
                if(possiblePosition.x >= 0 && possiblePosition.x < 28 && map.canMoveTo(possiblePosition) && !visited[possiblePosition.x][possiblePosition.y]){
                    lastPosition[possiblePosition.x][possiblePosition.y] = currentPosition;
                    visited[possiblePosition.x][possiblePosition.y] = true;
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

    private void setDirectionTowardsPlayer(){
        boolean[][] visited = new boolean[28][32];
        for(int i = 0; i < 28; i++){
            for(int j = 0; j < 32; j++){
                visited[i][j] = false;
            }
        }
        visited[this.position.x][this.position.y] = true;

        Vector2d[][] lastPosition = new Vector2d[28][32];
        Queue<Vector2d> positionsToVisit = new LinkedList<>();
        positionsToVisit.add(this.position);
        Vector2d currentPlayerPosition = map.getPlayer().getPosition();
        BFS(visited, positionsToVisit, lastPosition, currentPlayerPosition);

        Vector2d newPosition = getPositionToMove(lastPosition, currentPlayerPosition);
        this.direction = this.position.getDirectionTowardsVector(newPosition);
    }

    private void setRandomDirection(){
        Direction testDirection = this.direction;
        ArrayList<Direction> possibleDirections = new ArrayList<>();
        for(int i =0; i < 4; i++){
            Vector2d possiblePosition = this.position.add(testDirection.toUnitVector());
            if(possiblePosition.x == -1) possiblePosition = new Vector2d(27, possiblePosition.y);
            else if(possiblePosition.x == 28) possiblePosition = new Vector2d(0, possiblePosition.y);
            if(map.canMoveTo(possiblePosition))
                possibleDirections.add(testDirection);
            testDirection = testDirection.next();
        }
        int turnNumber = Ghost.rand.nextInt(possibleDirections.size());
        this.direction = possibleDirections.get(turnNumber);
    }

    @Override
    public void move(){
        if(map.getPlayer().isRespawning())
            setRandomDirection();
        else setDirectionTowardsPlayer();

        super.move();
    }

    public static Image getVulnerableImage() {
        return vulnerableImage;
    }

    public Image getInitialImage(){
        return this.initialImage;
    }

    @Override
    public String toString() {
        return "g";
    }
}