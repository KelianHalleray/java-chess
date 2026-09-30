package org.javachess.service;

import org.javachess.entity.*;
import org.javachess.enums.Color;

public class AttackService {

    public boolean isKingSafeAfterMovement(Piece piece, Chessboard chessboard, Position nextPosition) {
        Chessboard simulatedChessboard = chessboard.copy();
        Piece currentSimulatedPiece = simulatedChessboard.getPieceFromPosition(piece.getPosition());
        Piece nextPiece = simulatedChessboard.getPieceFromPosition(nextPosition);

        if (nextPiece != null) {
            simulatedChessboard.remove(nextPiece);
        }

        currentSimulatedPiece.setPosition(nextPosition);


        return !isKingAttacked(simulatedChessboard, currentSimulatedPiece.getColor());
    }

    public boolean isKingAttacked(Chessboard chessboard, Color color) {
        King king = chessboard.findKing(color);
        Position kingPosition = king.getPosition();

        for (Piece piece : chessboard.getPieces()) {
            boolean isEnemy = piece.getColor() != color;

            if (isEnemy && canAttack(piece, chessboard, kingPosition)) {
                return true;

            }
        }

        return false;
    }


    public boolean canAttack(Piece piece, Chessboard chessboard, Position position) {

        if (piece instanceof Pawn pawn) {
            return pawn.getAttackPattern().contains(position);
        }

        if (piece instanceof SlidingPiece slidingPiece) {
            return piece.getMovePattern().contains(position)
                    && chessboard.arePositionsEmpty(slidingPiece.getPathTo(position));
        }

        return piece.getMovePattern().contains(position);
    }

}
