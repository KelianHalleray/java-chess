package org.javachess.enums;

public enum Direction {
    UP_LEFT(-1, +1),
    UP_RIGHT(+1, +1),
    DOWN_LEFT(-1, -1),
    DOWN_RIGHT(+1, -1),
    UP(0, +1),
    DOWN(0 , -1),
    LEFT(-1, 0),
    RIGHT(+1, 0);

    private final int deltaX;
    private final int deltaY;

    Direction(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public int getDeltaX() {
        return deltaX;
    }

    public int getDeltaY() {
        return deltaY;
    }
}
