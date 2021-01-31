package Pacman.visualizer;

import Pacman.game.Map;
import Pacman.game.Vector2d;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class VizualizerFX {
    private final Pane root;
    private final Map map;
    private final Tile[][] grid = new Tile[28][32];

    public VizualizerFX(Map map){
        this.root = new Pane();
        this.map = map;


        for(int i = 0; i < 28; i++){
            for(int j = 0; j < 32; j++){
                Vector2d position = new Vector2d(i, j);
                System.out.println(this.map.objectAt(position));
                this.grid[i][j] = new Tile(20, position, this.map.objectAt(position));
                this.root.getChildren().add(this.grid[i][j]);
            }
        }

    }
    public Pane getRoot() {
        return root;
    }
}
