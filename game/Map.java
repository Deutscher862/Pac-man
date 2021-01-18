package game;

public class Map {
    private final AbstractDynamicMapElement[][] dynamicMapElements;
    private final AbstractStaticMapElement[][] staticMapElements;

    public Map(Vector2d size) {
        this.dynamicMapElements = new AbstractDynamicMapElement[size.x][size.y];
        this.staticMapElements = new AbstractStaticMapElement[size.x][size.y];
    }

    public void showMap(){
        for(int i = 0; i < 32; i++){
            for(int j = 0; j < 28; j++){
                Object object = objectAt(new Vector2d(j, i));
                if(object != null )
                    System.out.print(object.toString() + " ");
                else System.out.print("  ");
            }
            System.out.println();
        }
    }

    public void place(Object object){
        Vector2d position;
        if(object instanceof AbstractStaticMapElement){
            position = ((AbstractStaticMapElement) object).getPosition();
            if(!isOccupied(position)){
                this.staticMapElements[position.x][position.y] = (AbstractStaticMapElement) object;
            }
        } else if(object instanceof AbstractDynamicMapElement){
            position = ((AbstractDynamicMapElement) object).getPosition();
            if(!isOccupied(position)){
                this.dynamicMapElements[position.x][position.y] = (AbstractDynamicMapElement) object;
            }
        }
    }

    public boolean canMoveTo(Vector2d position){
        return !(objectAt(position) instanceof Wall);
    }

    public void removeStaticObject(AbstractStaticMapElement object){
        Vector2d position = object.getPosition();
        this.staticMapElements[position.x][position.y] = null;
    }

    public void updateDynamicElementPosition(Vector2d oldPosition, Vector2d newPosition){
        AbstractDynamicMapElement object = (AbstractDynamicMapElement) objectAt(oldPosition);
        this.dynamicMapElements[oldPosition.x][oldPosition.y] = null;
        this.dynamicMapElements[newPosition.x][newPosition.y] = object;
    }

    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    public Object objectAt(Vector2d position){
        if(this.dynamicMapElements[position.x][position.y] != null){
            return this.dynamicMapElements[position.x][position.y];
        }
        else {
            return this.staticMapElements[position.x][position.y];
        }
    }
}