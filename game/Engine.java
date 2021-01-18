package game;

import java.util.ArrayList;

public class Engine {
    private final Map map;
    private final FileScanner reader;
    private final ArrayList<Ghost> ghostList;
    private Player pacman;

    public Engine(){
        this.reader = new FileScanner();
        this.map = new Map(new Vector2d(28, 32));
        this.ghostList = new ArrayList<>();
        placeObjectsAtMap();
        this.map.showMap();
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
                case '1' -> object = new Coin(position);
                case '2' -> object = new Star(position);
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