package org.javachess.entity;

import java.util.List;

abstract public class Piece {
    private Position position;

    public Piece(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    abstract public List<Position> getMovePattern();
}
