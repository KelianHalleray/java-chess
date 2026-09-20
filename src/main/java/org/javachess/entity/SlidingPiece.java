package org.javachess.entity;

import org.javachess.enums.Color;
import org.javachess.enums.Direction;
import org.javachess.interfaces.PositionValidator;

import java.util.ArrayList;
import java.util.List;

public abstract class SlidingPiece extends Piece {

    public SlidingPiece(Position position, PositionValidator positionValidator, Color color) {
        super(position, positionValidator, color);
    }

    protected abstract List<Direction> getPossibleDirections();


    @Override
    public List<Position> getMovePattern() {
        PositionValidator positionValidator = getPositionValidator();

        List<Position> possiblePositions = new ArrayList<>();
        List<Direction> possibleDirections = getPossibleDirections();
        Position position = getPosition();

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

    public List<Position> getPathTo(Position destination) {

        int startPositionX = getPosition().getPosition_x();
        int startPositionY = getPosition().getPosition_y();
        int lastPositionX = destination.getPosition_x();
        int lastPositionY = destination.getPosition_y();
        List<Position> pathPosition = new ArrayList<>();

        int stepPositionX = 0;
        int stepPositionY = 0;

        if (startPositionX < lastPositionX) {
            stepPositionX = Direction.RIGHT.getDeltaX();
        }
        else if (startPositionX > lastPositionX) {
            stepPositionX = Direction.LEFT.getDeltaX();
        }

        if (startPositionY < lastPositionY) {
            stepPositionY = Direction.UP.getDeltaY();
        }
        else if (startPositionY > lastPositionY) {
            stepPositionY = Direction.DOWN.getDeltaY();
        }


        int currentX = startPositionX + stepPositionX;
        int currentY = startPositionY + stepPositionY;

        while (currentX != lastPositionX || currentY != lastPositionY) {

            pathPosition.add(new Position(currentX, currentY));
            currentX += stepPositionX;
            currentY += stepPositionY;
        }

        return pathPosition;
    }
}
