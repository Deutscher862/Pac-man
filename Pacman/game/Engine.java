package Pacman.game;

import javafx.stage.Stage;

import java.util.ArrayList;

public class Engine {
    private final Stage stage;
    private final Map map;
    private final FileScanner reader;
    private final ArrayList<Ghost> ghostList;
    private int roundNumber;
    private int lives;
    private int points;
    private Player pacman;

    public Engine(Stage stage){
        this.stage = stage;
        this.roundNumber = 1;
        this.reader = new FileScanner();
        this.map = new Map(new Vector2d(28, 32));
        this.ghostList = new ArrayList<>();
        placeObjectsAtMap();
        this.map.showMap();
        this.lives = 3;
        this.points = 0;

        stage.show();
    }

    public void run(){
        for(int i = 0; i < 100; i++){
            for(Ghost ghost : this.ghostList){
                ghost.move();
            }
            movePacman();
            this.map.showMap();
        }
    }

    private void movePacman(){
        pacman.move();
        AbstractStaticMapElement staticMapElement = this.map.getStaticElement(pacman.getPosition());
        if(staticMapElement != null){
            this.points += staticMapElement.getValue();
            this.map.removeStaticObject(staticMapElement);
        }
    }

    private void placeObjectsAtMap(){
        String stringMap = this.reader.readMapFromTxt();
        Vector2d position;
        int row = 0;
        int column = 0;
        for (char ch: stringMap.toCharArray()) {
            position = new Vector2d(column, row);
            Object object;
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
        System.out.println();
    }
}