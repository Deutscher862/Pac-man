package Pacman.game;

import Pacman.visualizer.VizualizerFX;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;

public class Engine {
    private final VizualizerFX vizualizer;
    private final Map map;
    private final Vector2d mapSize;
    private final FileScanner reader;
    private final ArrayList<Ghost> ghostList=  new ArrayList<>();
    private int ghostVelocity;
    private int roundNumber;
    private int lives;
    private int points;
    private Player pacman;
    private boolean paused;

    public Engine(Stage stage){
        this.ghostVelocity = 300;
        this.roundNumber = 1;
        this.reader = new FileScanner();
        this.paused = true;
        this.mapSize = new Vector2d(28, 32);
        this.map = new Map(this.mapSize, this);
        placeObjectsAtMap();
        this.vizualizer = new VizualizerFX(stage, this.map, this);
        this.lives = 3;
        this.points = 0;

        stage.setTitle("Pacman");
        stage.setScene(new Scene(vizualizer.getRoot(), 1000, 800, Color.BLACK));
        stage.show();
    }

    public void run(){
        this.paused = false;
        Thread playerMove = new Thread(() -> {
            while (!this.paused && lives > 0) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                moveDynamicElement(this.pacman);
                this.vizualizer.updateRightPanel();
            }
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            startNewRound();
                        }
                    },
                    2000
            );
        });
        playerMove.start();

        Thread ghostsMove = new Thread(() -> {
            while (!this.paused && lives > 0) {
                try {
                    Thread.sleep(this.ghostVelocity);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (Ghost ghost : this.ghostList)
                    moveDynamicElement(ghost);
            }
        });
        ghostsMove.start();
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

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void setPlayerDirection(Direction direction){
        this.pacman.setDirection(direction);
        this.pacman.rotateImage();
    }

    private void informAboutNewPosition(Vector2d oldPosition, AbstractDynamicMapElement object){
        Platform.runLater(()->{
            this.vizualizer.changeImage(oldPosition, this.map.objectAt(oldPosition));
            this.vizualizer.changeImage(object.getPosition(), object);
        });
    }

    private void moveDynamicElement(AbstractDynamicMapElement object){
        if((object instanceof Player && object.getDirection() == null) || object.isRespawning()) return;
        Vector2d oldPosition;
        Vector2d newPosition;
        oldPosition = object.getPosition();
        object.move();
        newPosition = object.getPosition();
        if(!oldPosition.equals(newPosition)) {
            StaticMapElement staticMapElement = this.map.getStaticElement(newPosition);
            if (object instanceof Player && staticMapElement != null) {
                //uruchamiam tryb powerUp
                if(staticMapElement.getType() == StaticElementType.Star) setPowerUpMode(true);
                this.points += staticMapElement.getPointValue();
                Platform.runLater(()-> this.vizualizer.changeImage(staticMapElement.getPosition(), null));
                this.map.removeStaticObject(staticMapElement);
                //jeśli gracz wejdzie na pole z duchem
                for(Ghost ghost: this.ghostList){
                    if(ghost.getPosition().equals(object.position))
                        killObject(ghost);
                }
            }
            //jeśli duch wejdzie na pole z graczem
            else if(object instanceof Ghost && object.getPosition().equals(this.pacman.getPosition())){
                killObject(object);
            }
            informAboutNewPosition(oldPosition, object);
        }
    }

    private void setPowerUpMode(Boolean powerUpOn) {
        this.pacman.setPowerUp(powerUpOn);
        for(Ghost ghost : this.ghostList){
            if(powerUpOn) ghost.setImage(Ghost.getVulnerableImage());
            else ghost.setImage(ghost.getInitialImage());
        }
        if(powerUpOn) {
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            setPowerUpMode(false);
                        }
                    },
                    2000
            );
        }
    }

    private void killObject(AbstractDynamicMapElement ghost){
        AbstractDynamicMapElement objectToBeKilled;
        if(!this.pacman.getPowerUp()){
            objectToBeKilled = this.pacman;
            this.lives -= 1;
            setPlayerDirection(null);
        }
        else objectToBeKilled = ghost;

        objectToBeKilled.setRespawning(true);
        Vector2d oldPosition = objectToBeKilled.getPosition();
        objectToBeKilled.setPosition(objectToBeKilled.getInitialPosition());
        informAboutNewPosition(oldPosition, objectToBeKilled);
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        objectToBeKilled.setRespawning(false);
                    }
                },
                3000
        );
    }

    public void generateFruit(){
        Vector2d newFruitPosition = this.mapSize.getRandomPosition();
        while(this.map.isOccupied(newFruitPosition))
            newFruitPosition = this.mapSize.getRandomPosition();
        StaticMapElement fruit = new StaticMapElement(newFruitPosition, "resources/fruit.png", 5*this.roundNumber, StaticElementType.Fruit);
        this.map.place(fruit);
        Platform.runLater(()-> this.vizualizer.changeImage(fruit.getPosition(), fruit));
    }

    public void startNewRound() {
        if(this.lives == 0){
            this.vizualizer.setGameOverInformation();
        }
        else {
            this.roundNumber += 1;
            if (this.ghostVelocity > 75)
                this.ghostVelocity -= 25;
            this.pacman.setDirection(null);

            this.map.clear();
            placeObjectsAtMap();
            this.vizualizer.resetGrid();
            run();
        }
    }

    public void endGame(){
        System.exit(0);
    }

    private void placeObjectsAtMap(){
        String stringMap = this.reader.readMapFromTxt();
        Vector2d position;
        int row = 0;
        int column = 0;
        for (char ch: stringMap.toCharArray()) {
            position = new Vector2d(column, row);
            AbstractMapElement object = null;
            switch (ch) {
                case '0' -> object = new StaticMapElement(position, "resources/wall.jpg", 0, StaticElementType.Wall);
                case '1' -> object = new StaticMapElement(position, "resources/coin.png", this.roundNumber, StaticElementType.Coin);
                case '2' -> object = new StaticMapElement(position, "resources/star.png", 15*this.roundNumber, StaticElementType.Star);
                case '4' -> {
                    if(this.roundNumber == 1) {
                        object = new Ghost(position, this.map, Direction.NORTH, this.ghostList.size() + 1);
                        this.ghostList.add((Ghost) object);
                    }
                }
                case '8' -> {
                    if(this.roundNumber == 1) {
                        object = new Player(position, this.map, null);
                        this.pacman = (Player) object;
                    }
                }
                case '9' -> {
                }
                default -> throw new IllegalStateException("Unexpected value: " + ch);
            }
            if (object != null) this.map.place(object);
            column += 1;
            if(column == 28){
                column = 0;
                row += 1;
            }
        }
        if(this.roundNumber > 1){
            this.pacman.setPosition(this.pacman.getInitialPosition());
            for(Ghost ghost : this.ghostList){
                ghost.setPosition(ghost.getInitialPosition());
            }
        }
    }
}