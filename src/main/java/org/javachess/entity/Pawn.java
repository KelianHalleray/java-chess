package org.javachess.entity;

import org.javachess.enums.Color;
import org.javachess.enums.Direction;
import org.javachess.interfaces.PositionValidator;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    public Pawn(Position position, PositionValidator positionValidator, Color color) {
        super(position, positionValidator, color);
    }

    @Override
    public List<Position> getMovePattern() {
        Position position = getPosition();
        List<Position> candidates = new ArrayList<>();

        int nextYPosition = position.getPosition_y() + getForwardDirection().getDeltaY();

        candidates.add(new Position(position.getPosition_x(), nextYPosition));

        return filterCandidates(candidates);
    }

    public List<Position> getAttackPattern() {
        Position position = getPosition();

        int nextYPosition = position.getPosition_y() + getForwardDirection().getDeltaY();

        List<Position> candidates = List.of(
                new Position(position.getPosition_x() - 1, nextYPosition),
                new Position(position.getPosition_x() + 1, nextYPosition)
        );

        return filterCandidates(candidates);
    }

    private Direction getForwardDirection() {
        if (getColor() == Color.BLACK) {
            return Direction.DOWN;
        }

        return Direction.UP;
    }
}
