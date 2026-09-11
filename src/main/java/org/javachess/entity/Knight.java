package org.javachess.entity;

import org.javachess.interfaces.PositionValidator;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {

    public Knight(Position position, PositionValidator positionValidator) {
        super(position, positionValidator);
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


}
