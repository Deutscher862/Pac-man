package Pacman.game;

import java.util.ArrayList;

public class Map {
    private Player player;
    private int numberOfCoins;
    private final Engine engine;
    private final ArrayList<Ghost> ghostList = new ArrayList<>();
    private final AbstractStaticMapElement[][] staticMapElements;

    public Map(Vector2d size, Engine engine) {
        this.staticMapElements = new AbstractStaticMapElement[size.x][size.y];
        this.engine = engine;
        this.numberOfCoins = 0;
    }

    public AbstractStaticMapElement getStaticElement(Vector2d position){
        return this.staticMapElements[position.x][position.y];
    }

    public int getNumberOfCoins() {
        return numberOfCoins;
    }

    public Player getPlayer() {
        return player;
    }

    public void place(AbstractMapElement object){
        //obiekty na mapie podzieliłem na statyczne(monety, owoce i gwiazdki) i dynamiczne(pacman oraz duchy)
        Vector2d position;
        if(object instanceof AbstractStaticMapElement){
            position = object.getPosition();
            if(!isOccupied(position)){
                //zwiększam licznik monet na mapie, by móc ustalić kiedy należy zakończyć grę
                if(object instanceof Coin) this.numberOfCoins += 1;
                this.staticMapElements[position.x][position.y] = (AbstractStaticMapElement) object;
            }
        } else if(object instanceof AbstractDynamicMapElement){
            if(object instanceof Player) this.player = (Player) object;
            else this.ghostList.add((Ghost) object);
        }
    }

    public boolean canMoveTo(Vector2d position){
        return !(objectAt(position) instanceof Wall);
    }

    public void removeStaticObject(AbstractStaticMapElement object){
        Vector2d position = object.getPosition();
        if(object instanceof Coin) this.numberOfCoins -= 1;
        if(this.numberOfCoins % 50 == 0) this.engine.generateFruit();
        this.staticMapElements[position.x][position.y] = null;
        if(this.numberOfCoins == 0) this.engine.startNewRound();
    }

    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    public AbstractMapElement objectAt(Vector2d position){
        if(this.player != null && this.player.getPosition().equals(position)) return this.player;
        else{
            for(Ghost ghost : this.ghostList){
                if (ghost.getPosition().equals(position)) return ghost;
            }
            return this.staticMapElements[position.x][position.y];
        }
    }
}