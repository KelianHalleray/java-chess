package org.javachess.service;

import org.javachess.entity.Chessboard;
import org.javachess.entity.Piece;
import org.javachess.entity.Position;
import org.javachess.entity.SlidingPiece;
import org.javachess.enums.Color;
import org.javachess.enums.Direction;

import java.util.ArrayList;
import java.util.List;

public class MoveService {

    public boolean isInMovePattern(Piece piece, Position position) {
        List<Position> possiblePositions = piece.getMovePattern();

        return possiblePositions.contains(position);
    }

    public boolean checkMovement(Piece piece, Chessboard chessboard, Position nextPosition) {
        Piece pieceAtDestination = chessboard.getPieceFromPosition(nextPosition);
        boolean isPositionEmpty = pieceAtDestination == null;
        boolean isEnemy = !isPositionEmpty && (pieceAtDestination.getColor() != piece.getColor());

        if (piece instanceof SlidingPiece slidingPiece) {
            return isInMovePattern(piece, nextPosition)
                    && hasNoPiecesOnPath(slidingPiece, chessboard, nextPosition)
                    && (isEnemy || isPositionEmpty);
        }

        return isInMovePattern(piece, nextPosition) && (isEnemy || isPositionEmpty);
    }

    public boolean hasNoPiecesOnPath(SlidingPiece piece, Chessboard chessboard, Position nextPosition) {
        List<Position> pathPositions = piece.getPathTo(nextPosition);

        for (Position position : pathPositions) {
            if (chessboard.getPieceFromPosition(position) != null) {
                return false;
            }
        }

        return true;

    }

}
