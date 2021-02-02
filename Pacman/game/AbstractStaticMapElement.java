package Pacman.game;

abstract class AbstractStaticMapElement extends AbstractMapElement{
    private final int value;

    public AbstractStaticMapElement(Vector2d position, int value, String path) {
        super(position, path);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
