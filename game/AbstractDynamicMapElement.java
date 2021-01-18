package game;

abstract class AbstractDynamicMapElement {
    private final Vector2d initialPosition;
    private Vector2d position;
    protected Direction direction;
    private static Map map;

    AbstractDynamicMapElement(Vector2d position, Map map, Direction direction){
        this.initialPosition = position;
        this.position = position;
        this.direction = direction;
        AbstractDynamicMapElement.map = map;
    }

    public void move(){
        Vector2d oldPosition = this.position;
        Vector2d newPosition;
        newPosition = this.position.Add(this.direction.toUnitVector());
        if(newPosition.x == -1) newPosition = new Vector2d(27, newPosition.y);
        else if(newPosition.x == 28) newPosition = new Vector2d(0, newPosition.y);
        if(map.canMoveTo(newPosition)){
            this.position = newPosition;
            positionChanged(oldPosition);
        }
    }

    private void positionChanged(Vector2d oldPosition){
        map.updateDynamicElementPosition(oldPosition, this.position);
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public Vector2d getInitialPosition() {
        return initialPosition;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}