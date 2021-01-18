package game;

abstract class AbstractStaticMapElement {
    protected final Vector2d position;

    public AbstractStaticMapElement(Vector2d position) {
        this.position = position;
    }

    public Vector2d getPosition() {
        return this.position;
    }
}
