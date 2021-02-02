package Pacman.game;

abstract class AbstractDynamicMapElement extends AbstractMapElement{
    private final Vector2d initialPosition;
    protected Direction direction;
    protected static Map map;

    AbstractDynamicMapElement(Vector2d position, Map map, Direction direction, String path){
        super(position, path);
        this.initialPosition = position;
        this.direction = direction;
        AbstractDynamicMapElement.map = map;
    }

    public Vector2d getInitialPosition() {
        return initialPosition;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void move(){
        Vector2d newPosition;
        newPosition = super.getPosition().Add(this.direction.toUnitVector());
        //jeśli wyjdzie po za mapę to wraca po drugiej stronie
        if(newPosition.x == -1) newPosition = new Vector2d(27, newPosition.y);
        else if(newPosition.x == 28) newPosition = new Vector2d(0, newPosition.y);
        if(map.canMoveTo(newPosition)){
            super.position = newPosition;
        }
    }
}