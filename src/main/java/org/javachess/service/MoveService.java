package org.javachess.service;

import org.javachess.entity.*;
import org.javachess.enums.Color;

import java.util.List;

public class MoveService {
    private final AttackService attackService = new AttackService();

    public boolean isInMovePattern(Piece piece, Position position) {
        List<Position> possiblePositions = piece.getMovePattern();

        return possiblePositions.contains(position);
    }

    public boolean checkMovement(Piece piece, Chessboard chessboard, Position nextPosition) {
        return checkBasicRules(piece, chessboard, nextPosition)
                && attackService.isKingSafeAfterMovement(piece, chessboard, nextPosition);
    }

    public void makeMovement(Piece piece, Chessboard chessboard, Position destination) {
        Piece pieceAtDestination = chessboard.getPieceFromPosition(destination);

        if (checkMovement(piece, chessboard, destination)) {
            if (pieceAtDestination != null) {
                chessboard.remove(pieceAtDestination);
            }
            piece.setPosition(destination);
        }
    }

    public boolean checkBasicRules(Piece piece, Chessboard chessboard, Position nextPosition) {
        Piece pieceAtDestination = chessboard.getPieceFromPosition(nextPosition);
        boolean isPositionEmpty = pieceAtDestination == null;
        boolean isEnemy = !isPositionEmpty && (pieceAtDestination.getColor() != piece.getColor());


        if (piece instanceof Pawn pawn) {

            if (isEnemy) {
                return pawn.getAttackPattern().contains(nextPosition);
            }

            if (pawn.isDoubleMove(nextPosition)) {
                Position pawnPositionInFront = pawn.getPositionInFront();

                if (chessboard.getPieceFromPosition(pawnPositionInFront) != null) {
                    return false;
                }
            }

        }

        if (piece instanceof SlidingPiece slidingPiece) {
            return isInMovePattern(piece, nextPosition)
                    && chessboard.arePositionsEmpty(slidingPiece.getPathTo(nextPosition))
                    && (isEnemy || isPositionEmpty);
        }


        return isInMovePattern(piece, nextPosition) && (isEnemy || isPositionEmpty);
    }




}
