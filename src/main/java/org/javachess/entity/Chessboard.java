package org.javachess.entity;

public class Chessboard {
    private static final int BOARD_SIZE = 8;

    public Chessboard() {
    }

    public boolean isValidPosition(Position position) {
        int x = position.getPosition_x();
        int y = position.getPosition_y();

        boolean isXValid = 0 <= x && BOARD_SIZE > x;
        boolean isYValid = 0 <= y && BOARD_SIZE > y;

        return isXValid && isYValid;
    }
}
