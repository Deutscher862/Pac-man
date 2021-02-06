package Pacman.game;

import javafx.scene.image.Image;

import java.util.*;

public class Ghost extends AbstractDynamicMapElement {
    private static Image vulnerableImage;
    private final Image initialImage;

    Ghost(Vector2d position, Map map, Direction direction, int number) {
        super(position, map, direction, "resources/ghost" + number + ".png");
        this.initialImage = new Image("resources/ghost" + number + ".png");
        Ghost.vulnerableImage = new Image("resources/vulnerableGhost.png");
    }

    public static Image getVulnerableImage() {
        return vulnerableImage;
    }

    private void BFS(boolean[][] visited, Queue<Vector2d> positionsToVisit, Vector2d[][] lastPosition, Vector2d targetPosition){
        while(!positionsToVisit.isEmpty()){
            //zdejmuję pozycję z kolejki
            Vector2d currentPosition = positionsToVisit.remove();
            if(currentPosition.equals(targetPosition)) return;
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

    private void setDirectionTowardsPosition(Vector2d targetPosition){
        if(this.position.equals(targetPosition)){
            this.direction = null;
            return;
        }

        boolean[][] visited = new boolean[28][32];
        for(int i = 0; i < 28; i++){
            for(int j = 0; j < 32; j++){
                visited[i][j] = false;
            }
        }
        visited[this.position.x][this.position.y] = true;

        Vector2d[][] lastPosition = new Vector2d[28][32];
        lastPosition[this.position.x][this.position.y] = this.position;
        Queue<Vector2d> positionsToVisit = new LinkedList<>();
        positionsToVisit.add(this.position);
        BFS(visited, positionsToVisit, lastPosition, targetPosition);

        Vector2d newPosition = getPositionToMove(lastPosition, targetPosition);
        this.direction = this.position.getDirectionTowardsVector(newPosition);
    }

    @Override
    public void move(){
        Player pacman = map.getPlayer();
        //jeśli gracz się odradza lub zjadł gwiazdkę, duchy wracają na pozycje startowe
        if(pacman.isRespawning() || pacman.getPowerUp())
            setDirectionTowardsPosition(this.getInitialPosition());
        //w przeciwnym wypadku duchy gonią gracza
        else setDirectionTowardsPosition(map.getPlayer().getPosition());
        if(this.direction == null) return;
        super.move();
    }

    public Image getInitialImage(){
        return this.initialImage;
    }
}