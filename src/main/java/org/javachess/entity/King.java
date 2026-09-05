package org.javachess.entity;

import java.util.List;

public class King extends Piece {

    public King(Position position) {
        super(position);
    }

    @Override
    public List<Position> getMovePattern() {
        Position position = getPosition();

        return List.of(
                new Position(position.getPosition_x() - 1, position.getPosition_y() + 1),
                new Position(position.getPosition_x(), position.getPosition_y() + 1),
                new Position(position.getPosition_x() + 1, position.getPosition_y() + 1),
                new Position(position.getPosition_x() + 1, position.getPosition_y()),
                new Position(position.getPosition_x() + 1, position.getPosition_y() - 1),
                new Position(position.getPosition_x(), position.getPosition_y() - 1),
                new Position(position.getPosition_x() - 1, position.getPosition_y() - 1),
                new Position(position.getPosition_x() - 1, position.getPosition_y())

        );
    }
}
