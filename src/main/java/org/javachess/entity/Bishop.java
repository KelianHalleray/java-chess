package org.javachess.entity;

import org.javachess.enums.Color;
import org.javachess.enums.Direction;
import org.javachess.interfaces.PositionValidator;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends SlidingPiece {
    private final List<Direction> POSSIBLE_DIRECTIONS = List.of(
            Direction.UP_LEFT,
            Direction.UP_RIGHT,
            Direction.DOWN_LEFT,
            Direction.DOWN_RIGHT
    );

    public Bishop(Position position, PositionValidator positionValidator, Color color) {
        super(position, positionValidator, color);
    }

    @Override
    protected List<Direction> getPossibleDirections() {
        return POSSIBLE_DIRECTIONS;
    }

    @Override
    public Piece copy(PositionValidator positionValidator) {
        return new Bishop(getPosition(), positionValidator, getColor());
    }


}
