package Pacman.game;

class StaticMapElement extends AbstractMapElement{
    private final StaticElementType type;
    private final int pointValue;

    public StaticMapElement(Vector2d position, String path, int value,  StaticElementType type) {
        //elementy statyczne różnią się jedynie wartością punktową i zdjęciem, więc zamiast osobnych klas zawierają enum odpowiedniego typu
        super(position, path);
        this.type = type;
        this.pointValue = value;
    }

    public StaticElementType getType() {
        return type;
    }

    public int getPointValue() {
        return pointValue;
    }
}