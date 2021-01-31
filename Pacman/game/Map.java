package Pacman.game;

import java.util.ArrayList;

public class Map {
    private Player player;
    private int numberOfCoins;
    private final ArrayList<Ghost> ghostList = new ArrayList<>();
    private final AbstractStaticMapElement[][] staticMapElements;

    public Map(Vector2d size) {
        this.staticMapElements = new AbstractStaticMapElement[size.x][size.y];
        this.numberOfCoins = 0;
    }

    public void place(AbstractMapElement object){
        //obiekty na mapie podzieliłem na statyczne(monety, owoce i gwiazdki) i dynamiczne(pacman oraz duchy)
        Vector2d position;
        if(object instanceof AbstractStaticMapElement){
            position = object.getPosition();
            if(!isOccupied(position)){
                //zwiększam licznik monet i gwiazdek na mapie, by móc ustalić kiedy należy zakończyć grę
                if(!(object instanceof Fruit)) this.numberOfCoins += 1;
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
        if(!(object instanceof Fruit)) this.numberOfCoins -= 1;
        this.staticMapElements[position.x][position.y] = null;
    }

    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    public AbstractStaticMapElement getStaticElement(Vector2d position){
        return this.staticMapElements[position.x][position.y];
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