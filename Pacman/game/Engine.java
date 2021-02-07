package Pacman.game;

import Pacman.visualizer.AudioPlayer;
import Pacman.visualizer.VizualizerFX;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;

public class Engine {
    private final FileScanner reader;
    private final Vector2d mapSize;
    private final Map map;
    private final VizualizerFX vizualizer;
    private final AudioPlayer player;
    private final ArrayList<Ghost> ghostList=  new ArrayList<>();
    private int ghostVelocity;
    private int powerUpTime;
    private int roundNumber;
    private int lives;
    private int points;
    private Player pacman;
    private boolean paused;

    public Engine(Stage stage){
        this.reader = new FileScanner();
        this.mapSize = new Vector2d(28, 32);
        this.map = new Map(this.mapSize, this);
        this.roundNumber = 1;
        placeObjectsAtMap();
        this.vizualizer = new VizualizerFX(stage, this.map, this);
        this.player = new AudioPlayer();
        this.ghostVelocity = 300;
        this.powerUpTime= 5000;
        this.paused = true;
        this.lives = 3;
        this.points = 0;

        stage.setTitle("Pacman");
        stage.setScene(new Scene(vizualizer.getRoot(), 1000, 800, Color.BLACK));
        stage.show();
        this.player.playSound("src/resources/audio/pacman_beginning.wav");
    }

    public void run(){
        this.paused = false;

        //2 osobne wątki do poruszania pacmanem oraz duchami
        Thread playerMove = new Thread(() -> {
            while (!this.paused && lives > 0) {
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                moveDynamicElement(this.pacman);
                this.vizualizer.updateRightPanel();
            }
            //przed rozpoczęciem nowej rundy czekam na ewentualny ostatni ruch duch
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            try {
                                startNewRound();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    500
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
                switch (staticMapElement.getType()){
                    case Star -> setPowerUpMode(true);
                    case Fruit -> this.player.playSound("src/resources/audio/pacman_eatfruit.wav");
                    case Coin -> this.player.playChompSound();
                    default -> {
                    }
                }

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
            //ustawinie wyłączenia powerUp'a z opóźnieniem
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            setPowerUpMode(false);
                        }
                    },
                    this.powerUpTime
            );
        }
    }

    private void killObject(AbstractDynamicMapElement ghost){
        //przekazany jest duch, który znalazł się na tym samym polu co gracz
        AbstractDynamicMapElement objectToBeKilled;
        //wybierany jest obiekt, który zostaje przeniesiony, w zależności, czy powerUp jest włączony
        if(!this.pacman.getPowerUp()){
            objectToBeKilled = this.pacman;
            this.lives -= 1;
            player.playSound("src/resources/audio/pacman_death.wav");
            setPlayerDirection(null);
        }
        else{
            objectToBeKilled = ghost;
            player.playDeadGhostSound();
        }

        //zabity obiekt się odradza, w tym czasie nie może się ruszyć
        objectToBeKilled.setRespawning(true);

        //zabity obiekt zostaje przeniesiony na swoją pierwotną pozycję
        Vector2d oldPosition = objectToBeKilled.getPosition();
        objectToBeKilled.setPosition(objectToBeKilled.getInitialPosition());
        informAboutNewPosition(oldPosition, objectToBeKilled);

        //koniec odradzania po odpowiednim czasie
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
        //na mapie zostaje umieszczony owoc na losowej pustej pozycji
        Vector2d newFruitPosition = this.mapSize.getRandomPosition();
        while(this.map.isOccupied(newFruitPosition))
            newFruitPosition = this.mapSize.getRandomPosition();
        StaticMapElement fruit = new StaticMapElement(newFruitPosition, "resources/fruit.png", 5*this.roundNumber, StaticElementType.Fruit);
        this.map.place(fruit);
        Platform.runLater(()-> this.vizualizer.changeImage(fruit.getPosition(), fruit));
    }

    public void startNewRound() throws InterruptedException {
        //jeśli gracz nie ma żyć, gra się kończy
        if(this.lives == 0){
            this.vizualizer.setGameOverInformation();
        }
        //przed nową rundą ustawiany jest większy poziom trudności oraz elementy na mapie resetują się
        else {
            this.roundNumber += 1;
            if (this.ghostVelocity > 150)
                this.ghostVelocity -= 50;
            if(this.powerUpTime > 2000)
                this.powerUpTime -= 500;
            this.pacman.setDirection(null);

            this.map.clear();
            placeObjectsAtMap();
            this.vizualizer.resetGrid();
            this.vizualizer.setTimer(3);
            run();
        }
    }

    public void endGame(){
        System.exit(0);
    }

    private void placeObjectsAtMap(){
        //umiejscowanie wczytanych elementów na mapie
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
        //jeśli elementy ruchome mapy już na niej są, ustawiam je na ich pierwotnej pozycji
        if(this.roundNumber > 1){
            this.pacman.setPosition(this.pacman.getInitialPosition());
            for(Ghost ghost : this.ghostList){
                ghost.setPosition(ghost.getInitialPosition());
            }
        }
    }
}