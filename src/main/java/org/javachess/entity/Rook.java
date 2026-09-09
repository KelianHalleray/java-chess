package org.javachess.entity;

import org.javachess.enums.Direction;
import org.javachess.interfaces.PositionValidator;

import java.util.ArrayList;
import java.util.List;

public class Rook extends SlidingPiece {

    private static final List<Direction> POSSIBLE_DIRECTIONS = List.of(
            Direction.UP,
            Direction.DOWN,
            Direction.LEFT,
            Direction.RIGHT
            );

    public Rook(Position position, PositionValidator positionValidator) {
        super(position, positionValidator);
    }

    @Override
    protected List<Direction> getPossibleDirections() {
        return POSSIBLE_DIRECTIONS;
    }

}
