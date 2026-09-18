package org.javachess.entity;

import org.javachess.enums.Color;
import org.javachess.interfaces.PositionValidator;

import java.util.List;

public class Pawn extends Piece {

    public Pawn(Position position, PositionValidator positionValidator, Color color) {
        super(position, positionValidator, color);
    }

    @Override
    public List<Position> getMovePattern() {
        Position position = getPosition();
        List<Position> candidates = List.of(
                new Position(position.getPosition_x(), position.getPosition_y() + 1),
                new Position(position.getPosition_x(), position.getPosition_y() + 2)
        );

        return filterCandidates(candidates);
    }

    public List<Position> getAttackPattern() {
        Position position = getPosition();
        List<Position> candidates = List.of(
                new Position(position.getPosition_x() - 1, position.getPosition_y() + 1),
                new Position(position.getPosition_x() + 1, position.getPosition_y() + 1)
        );

        return filterCandidates(candidates);
    }
}
