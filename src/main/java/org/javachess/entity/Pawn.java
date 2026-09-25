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
        int nextYPositionDouble = position.getPosition_y() + (getForwardDirection().getDeltaY() * 2);

        if (isOnInitialRank(position)) {
            candidates.add(new Position(position.getPosition_x(), nextYPositionDouble));
        }

        candidates.add(new Position(position.getPosition_x(), nextYPosition));

        return filterCandidates(candidates);
    }

    @Override
    public Piece copy(PositionValidator positionValidator) {
        return new Pawn(getPosition(), positionValidator, getColor());
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

    private boolean isOnInitialRank(Position position) {
        return getColor() == Color.WHITE && position.getPosition_y() == 1
                || getColor() == Color.BLACK && position.getPosition_y() == 6;
    }

    private Direction getForwardDirection() {
        if (getColor() == Color.BLACK) {
            return Direction.DOWN;
        }

        return Direction.UP;
    }

    public Position getPositionInFront() {
        Position currentPosition = getPosition();

        int nextPositionY = currentPosition.getPosition_y() + getForwardDirection().getDeltaY();

        return new Position(currentPosition.getPosition_x(), nextPositionY);
    }

    public boolean isDoubleMove(Position destination) {
        int currentY = getPosition().getPosition_y();
        int nextY = destination.getPosition_y();

        return Math.abs(currentY - nextY) == 2;
    }
}
