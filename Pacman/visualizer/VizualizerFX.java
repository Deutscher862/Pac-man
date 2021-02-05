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
    private final Canvas canvas;
    private final Tile[][] grid = new Tile[28][32];

    public VizualizerFX(Stage stage, Map map, Engine engine){
        this.root = new Pane();
        this.map = map;
        this.engine = engine;
        this.canvas = new Canvas(600, 800);
        this.canvas.setTranslateX(30);
        this.canvas.setTranslateY(50);
        this.root.getChildren().add(this.canvas);

        this.rightPanel = new Text();
        this.rightPanel.setFill(Color.WHITE);
        this.rightPanel.setFont(Font.font("Verdana", 35));
        this.rightPanel.setTranslateX(600);
        this.rightPanel.setTranslateY(40);
        this.root.getChildren().add(rightPanel);

        for(int i = 0; i < 28; i++){
            for(int j = 0; j < 32; j++){
                Vector2d position = new Vector2d(i, j);
                this.grid[i][j] = new Tile(this.canvas, 20, position, this.map.objectAt(position));
                this.root.getChildren().add(this.grid[i][j]);
            }
        }

        stage.addEventFilter(KeyEvent.KEY_PRESSED, (key) -> {
            switch (key.getCode()) {
                case UP,W -> engine.setPlayerDirection(Direction.NORTH);
                case RIGHT,D -> engine.setPlayerDirection(Direction.EAST);
                case DOWN,S -> engine.setPlayerDirection(Direction.SOUTH);
                case LEFT,A -> engine.setPlayerDirection(Direction.WEST);
                //case ESCAPE -> engine.end();
            }
        });
    }
    public Pane getRoot() {
        return root;
    }

    public void changeImage(Vector2d position, AbstractMapElement object){
        this.grid[position.x][position.y].setImage(null);
        this.grid[position.x][position.y].setImage(object);
    }

    public void UpdateRightPanel(){
        this.rightPanel.setText("Points: " + this.engine.getPoints()
                            + "\nLives: " + this.engine.getLives()
                            + "\nRound: " + this.engine.getRound()
                            + "\nCoins left: " + this.map.getNumberOfCoins()
        );
    }
}