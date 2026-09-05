package org.javachess.entity;

import java.util.List;

public class Knight extends Piece{

    public Knight(Position position) {
        super(position);
    }

    @Override
    public List<Position> getMovePattern() {
        Position position = getPosition();

        return List.of(
                new Position(position.getPosition_x() - 1, position.getPosition_y() + 2),
                new Position(position.getPosition_x() + 1, position.getPosition_y() + 2),
                new Position(position.getPosition_x() + 2, position.getPosition_y() + 1),
                new Position(position.getPosition_x() + 2, position.getPosition_y() - 1),
                new Position(position.getPosition_x() + 1, position.getPosition_y() - 2),
                new Position(position.getPosition_x() - 1, position.getPosition_y() - 2),
                new Position(position.getPosition_x() - 2, position.getPosition_y() - 1),
                new Position(position.getPosition_x() - 2, position.getPosition_y() + 1)
        );
    }


}
