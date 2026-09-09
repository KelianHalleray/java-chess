package org.javachess.entity;

import org.javachess.enums.Direction;
import org.javachess.interfaces.PositionValidator;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {
    private final PositionValidator positionValidator;
    private final List<Direction> possibleDirections = List.of(
            Direction.UP_LEFT,
            Direction.UP_RIGHT,
            Direction.DOWN_LEFT,
            Direction.DOWN_RIGHT
    );

    public Bishop(Position position, PositionValidator positionValidator) {
        super(position);
        this.positionValidator = positionValidator;
    }

    @Override
    public List<Position> getMovePattern() {
        Position position = getPosition();

        List<Position> possiblePositions = new ArrayList<>();

        for (Direction direction : possibleDirections) {
            int x = position.getPosition_x() + direction.getDeltaX();
            int y = position.getPosition_y() + direction.getDeltaY();

            while (positionValidator.isValidPosition(new Position(x, y))) {
                possiblePositions.add(new Position(x, y));
                x += direction.getDeltaX();
                y += direction.getDeltaY();
            }

        }



        return possiblePositions;
    }


}
