package org.javachess.entity;

import org.javachess.interfaces.PositionValidator;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    public King(Position position, PositionValidator positionValidator) {
        super(position, positionValidator);
    }

    @Override
    public List<Position> getMovePattern() {
        Position position = getPosition();
        List<Position> candidates = List.of(
                new Position(position.getPosition_x() - 1, position.getPosition_y() + 1),
                new Position(position.getPosition_x(), position.getPosition_y() + 1),
                new Position(position.getPosition_x() + 1, position.getPosition_y() + 1),
                new Position(position.getPosition_x() + 1, position.getPosition_y()),
                new Position(position.getPosition_x() + 1, position.getPosition_y() - 1),
                new Position(position.getPosition_x(), position.getPosition_y() - 1),
                new Position(position.getPosition_x() - 1, position.getPosition_y() - 1),
                new Position(position.getPosition_x() - 1, position.getPosition_y())

        );

        return filterCandidates(candidates);
    }
}
