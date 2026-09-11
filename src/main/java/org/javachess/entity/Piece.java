package org.javachess.entity;

import org.javachess.interfaces.PositionValidator;

import java.util.ArrayList;
import java.util.List;

abstract public class Piece {
    private Position position;
    private final PositionValidator positionValidator;

    public Piece(Position position, PositionValidator positionValidator) {
        this.position = position;
        this.positionValidator = positionValidator;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    protected List<Position> filterCandidates(List<Position> candidates) {
        List<Position> validatedPositions = new ArrayList<>();

        for (Position candidate : candidates) {
            if (getPositionValidator().isValidPosition(candidate)) {
                validatedPositions.add(candidate);
            }
        }

        return validatedPositions;
    }

    public PositionValidator getPositionValidator() {
        return positionValidator;
    }

    abstract public List<Position> getMovePattern();
}
