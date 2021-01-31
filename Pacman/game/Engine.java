package Pacman.game;

import Pacman.visualizer.VizualizerFX;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Engine {
    private final Stage stage;
    private final VizualizerFX vizualizer;
    private final Map map;
    private int refreshTime;
    private final FileScanner reader;
    private final ArrayList<Ghost> ghostList=  new ArrayList<>();
    private int roundNumber;
    private int lives;
    private int points;
    private Player pacman;

    public Engine(Stage stage){
        this.refreshTime = 100;
        this.stage = stage;
        this.roundNumber = 1;
        this.reader = new FileScanner();
        this.map = new Map(new Vector2d(28, 32));
        placeObjectsAtMap();
        this.vizualizer = new VizualizerFX(stage, this.map, this);
        this.lives = 3;
        this.points = 0;

        this.stage.setTitle("Pacman");
        this.stage.setScene(new Scene(vizualizer.getRoot(), 1000, 800, Color.BLACK));
        this.stage.show();
    }

    public void run(){
        new Thread(() ->{
            while(lives > 0) {
                try {
                    Thread.sleep(this.refreshTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                moveDynamicElement(this.pacman);
                for (Ghost ghost : this.ghostList) {
                    moveDynamicElement(ghost);
                }
                this.vizualizer.UpdateRightPanel();
            }
        }).start();
    }

    public int getLives() {
        return lives;
    }

    public int getPoints() {
        return points;
    }

    public int getRound() {
        return this.roundNumber;
    }

    public void setPlayerDirection(Direction direction){
        this.pacman.setDirection(direction);
    }

    private void informAboutNewPosition(Vector2d oldPosition, AbstractDynamicMapElement object){
        this.vizualizer.changeColor(oldPosition, this.map.objectAt(oldPosition));
        this.vizualizer.changeColor(object.getPosition(), object);
    }

    private void moveDynamicElement(AbstractDynamicMapElement object){
        Vector2d oldPosition;
        Vector2d newPosition;
        oldPosition = object.getPosition();
        object.move();
        newPosition = object.getPosition();
        if(!oldPosition.equals(newPosition)) {
            AbstractStaticMapElement staticMapElement = this.map.getStaticElement(pacman.getPosition());
            if (object instanceof Player && staticMapElement != null) {
                this.points += staticMapElement.getValue();
                this.map.removeStaticObject(staticMapElement);
            }
            //jeśli duch i pacman są na tym samym polu
            else if(object instanceof Ghost && object.getPosition().equals(this.pacman.getPosition())){
                if(!this.pacman.getPowerUp()){
                    this.lives -= 1;
                    kill(this.pacman);
                }
                else kill(object);

            }
            informAboutNewPosition(oldPosition, object);
        }
    }

    private void kill(AbstractDynamicMapElement object){
        Vector2d oldPosition = object.getPosition();
        object.setPosition(object.getInitialPosition());
        informAboutNewPosition(oldPosition, object);
    }

    private void placeObjectsAtMap(){
        String stringMap = this.reader.readMapFromTxt();
        Vector2d position;
        int row = 0;
        int column = 0;
        for (char ch: stringMap.toCharArray()) {
            position = new Vector2d(column, row);
            AbstractMapElement object;
            switch (ch) {
                case '0' -> object = new Wall(position);
                case '1' -> object = new Coin(position, this.roundNumber);
                case '2' -> object = new Star(position, this.roundNumber);
                case '4' -> {
                    object = new Ghost(position, this.map, Direction.NORTH);
                    this.ghostList.add((Ghost) object);
                }
                case '8' -> {
                    object = new Player(position, this.map, Direction.EAST);
                    this.pacman = (Player) object;
                }
                case '9' -> object = null;
                default -> throw new IllegalStateException("Unexpected value: " + ch);
            }
            if (object != null) this.map.place(object);
            column += 1;
            if(column == 28){
                column = 0;
                row += 1;
            }
        }
    }
}