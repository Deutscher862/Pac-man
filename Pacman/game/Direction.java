package Pacman.game;

public enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    private final Vector2d northVector = new Vector2d(0, -1);
    private final Vector2d eastVector = new Vector2d(1, 0);
    private final Vector2d southVector = new Vector2d(0, 1);
    private final Vector2d westVector = new Vector2d(-1, 0);


    public Vector2d toUnitVector(){
        return switch (this) {
            case NORTH -> northVector;
            case EAST -> eastVector;
            case SOUTH -> southVector;
            case WEST -> westVector;
            default -> null;
        };
    }

    public Direction next(){
        return switch (this) {
            case NORTH -> Direction.EAST;
            case EAST -> Direction.SOUTH;
            case SOUTH -> Direction.WEST;
            case WEST -> Direction.NORTH;
            default -> null;
        };
    }
}