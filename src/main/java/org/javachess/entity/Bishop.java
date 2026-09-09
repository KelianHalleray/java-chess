package org.javachess.entity;

import org.javachess.interfaces.PositionValidator;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {
    private final PositionValidator positionValidator;

    public Bishop(Position position, PositionValidator positionValidator) {
        super(position);
        this.positionValidator = positionValidator;
    }

    @Override
    public List<Position> getMovePattern() {
        Position position = getPosition();


        List<Position> possiblePositions = new ArrayList<>();

       for (int x = position.getPosition_x() + 1, y = position.getPosition_y() + 1; positionValidator.isValidPosition(new Position(x, y)); x++, y++) {
           possiblePositions.add(new Position(x, y));
       }

       for (int x = position.getPosition_x() + 1, y = position.getPosition_y() - 1;  positionValidator.isValidPosition(new Position(x, y)); x++, y--) {
           possiblePositions.add(new Position(x, y));
       }

        for (int x = position.getPosition_x() - 1, y = position.getPosition_y() - 1;  positionValidator.isValidPosition(new Position(x, y)); x--, y--) {
            possiblePositions.add(new Position(x, y));
        }

        for (int x = position.getPosition_x() - 1, y = position.getPosition_y() + 1;  positionValidator.isValidPosition(new Position(x, y)); x--, y++) {
            possiblePositions.add(new Position(x, y));
        }

        return possiblePositions;
    }


}
