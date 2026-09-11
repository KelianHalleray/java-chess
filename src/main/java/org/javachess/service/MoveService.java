package org.javachess.service;

import org.javachess.entity.Piece;
import org.javachess.entity.Position;

import java.util.List;

public class MoveService {

    public boolean isInMovePattern(Piece piece, Position position) {
        List<Position> possiblePositions = piece.getMovePattern();

        return possiblePositions.contains(position);
    }
}
