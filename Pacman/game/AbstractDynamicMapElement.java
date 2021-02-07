package Pacman.game;

abstract class AbstractDynamicMapElement extends AbstractMapElement{
    protected static Map map;
    private final Vector2d initialPosition;
    protected Direction direction;
    private boolean respawning;

    AbstractDynamicMapElement(Vector2d position, Map map, Direction direction, String path){
        super(position, path);
        this.initialPosition = position;
        this.direction = direction;
        this.respawning = false;
        AbstractDynamicMapElement.map = map;
    }

    public Vector2d getInitialPosition() {
        return initialPosition;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean isRespawning() {
        return respawning;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setRespawning(boolean respawning) {
        this.respawning = respawning;
    }

    public void move(){
        Vector2d newPosition;
        newPosition = super.getPosition().add(this.direction.toUnitVector());

        //jeśli wyjdzie po za mapę to wraca po drugiej stronie
        if(newPosition.x == -1) newPosition = new Vector2d(27, newPosition.y);
        else if(newPosition.x == 28) newPosition = new Vector2d(0, newPosition.y);

        if(map.canMoveTo(newPosition)){
            super.position = newPosition;
        }
    }
}