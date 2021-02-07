package Pacman.visualizer;

import Pacman.game.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.scene.input.KeyEvent;

public class VizualizerFX {
    private final Pane root;
    private final Map map;
    private final Engine engine;
    private final Text rightPanel;
    private final Text gameOverInformation;
    private final Text timer;
    private final Tile[][] grid = new Tile[28][32];

    public VizualizerFX(Stage stage, Map map, Engine engine){
        this.root = new Pane();
        this.map = map;
        this.engine = engine;
        Canvas canvas = new Canvas(600, 800);
        canvas.setTranslateX(30);
        canvas.setTranslateY(50);
        this.root.getChildren().add(canvas);

        //panel informujący o statystkach gracza
        this.rightPanel = new Text();
        this.rightPanel.setFill(Color.WHITE);
        this.rightPanel.setFont(Font.font("Verdana", 35));
        this.rightPanel.setTranslateX(650);
        this.rightPanel.setTranslateY(100);
        this.root.getChildren().add(rightPanel);

        //informacja o końcu gry
        this.gameOverInformation = new Text();
        this.gameOverInformation.setText("Game over :(");
        this.gameOverInformation.setFill(Color.WHITE);
        this.gameOverInformation.setFont(Font.font("Verdana", 50));
        this.gameOverInformation.setTranslateX(620);
        this.gameOverInformation.setTranslateY(400);
        this.gameOverInformation.setVisible(false);
        this.root.getChildren().add(gameOverInformation);

        this.timer = new Text();
        this.timer.setFill(Color.WHITE);
        this.timer.setFont(Font.font("Verdana", 40));
        this.timer.setTranslateX(300);
        this.timer.setTranslateY(405);
        this.root.getChildren().add(timer);



        for(int i = 0; i < 28; i++){
            for(int j = 0; j < 32; j++){
                Vector2d position = new Vector2d(i, j);
                this.grid[i][j] = new Tile(canvas, 20, position, this.map.objectAt(position));
                this.root.getChildren().add(this.grid[i][j]);
            }
        }

        stage.addEventFilter(KeyEvent.KEY_PRESSED, (key) -> {
            switch (key.getCode()) {
                case UP,W -> engine.setPlayerDirection(Direction.NORTH);
                case RIGHT,D -> engine.setPlayerDirection(Direction.EAST);
                case DOWN,S -> engine.setPlayerDirection(Direction.SOUTH);
                case LEFT,A -> engine.setPlayerDirection(Direction.WEST);
                case ESCAPE -> engine.endGame();
            }
        });

        this.timer.toFront();
    }
    public Pane getRoot() {
        return root;
    }

    public void setGameOverInformation(){
        this.gameOverInformation.setVisible(true);
    }

    public void setTimer(int seconds) throws InterruptedException {
        while(seconds >= 0){
            if(seconds == 0) this.timer.setText("GO");
            else this.timer.setText(String.valueOf(seconds));
            Thread.sleep(1000);
            seconds -= 1;
        }
        this.timer.setText("");
    }

    public void changeImage(Vector2d position, AbstractMapElement object){
        this.grid[position.x][position.y].setImage(null);
        this.grid[position.x][position.y].setImage(object);
    }

    public void resetGrid(){
        Vector2d position;
        for(int i = 0; i < 28; i++){
            for(int j = 0; j < 32; j++){
                position = new Vector2d(i, j);
                this.grid[position.x][position.y].setImage(this.map.objectAt(position));
            }
        }
        updateRightPanel();
    }

    public void updateRightPanel(){
        this.rightPanel.setText("Points: " + this.engine.getPoints()
                            + "\nLives: " + this.engine.getLives()
                            + "\nRound: " + this.engine.getRound()
                            + "\nCoins left: " + this.map.getNumberOfCoins()
        );
    }
}