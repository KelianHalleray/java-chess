package org.javachess.entity;

import org.javachess.enums.Color;
import org.javachess.interfaces.PositionValidator;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {

    public Knight(Position position, PositionValidator positionValidator, Color color) {
        super(position, positionValidator, color);
    }

    @Override
    public List<Position> getMovePattern() {
        Position position = getPosition();
        List<Position> candidates = List.of(
                new Position(position.getPosition_x() - 1, position.getPosition_y() + 2),
                new Position(position.getPosition_x() + 1, position.getPosition_y() + 2),
                new Position(position.getPosition_x() + 2, position.getPosition_y() + 1),
                new Position(position.getPosition_x() + 2, position.getPosition_y() - 1),
                new Position(position.getPosition_x() + 1, position.getPosition_y() - 2),
                new Position(position.getPosition_x() - 1, position.getPosition_y() - 2),
                new Position(position.getPosition_x() - 2, position.getPosition_y() - 1),
                new Position(position.getPosition_x() - 2, position.getPosition_y() + 1)
        );

        return filterCandidates(candidates);
    }

    @Override
    public Piece copy(PositionValidator positionValidator) {
        return new Knight(getPosition(), positionValidator, getColor());
    }


}
