package Pacman.game;

abstract class AbstractStaticMapElement {
    protected final Vector2d position;
    private final int value;

    public AbstractStaticMapElement(Vector2d position, int value) {
        this.position = position;
        this.value = value;
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public int getValue() {
        return value;
    }
}
