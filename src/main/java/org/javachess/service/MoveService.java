package org.javachess.service;

import org.javachess.entity.*;
import org.javachess.enums.Color;

import java.awt.*;
import java.util.List;

public class MoveService {

    public boolean isInMovePattern(Piece piece, Position position) {
        List<Position> possiblePositions = piece.getMovePattern();

        return possiblePositions.contains(position);
    }

    public boolean checkMovement(Piece piece, Chessboard chessboard, Position nextPosition) {
        return checkBasicRules(piece, chessboard, nextPosition)
                && checkKingSafeRules(piece, chessboard, nextPosition);
    }

    public boolean arePositionsEmpty(Chessboard chessboard, List<Position> positions) {
        for (Position position : positions) {
            if (chessboard.getPieceFromPosition(position) != null) {
                return false;
            }
        }

        return true;
    }

    public boolean hasNoPiecesOnPath(SlidingPiece piece, Chessboard chessboard, Position nextPosition) {
        List<Position> pathPositions = piece.getPathTo(nextPosition);

        return arePositionsEmpty(chessboard, pathPositions);
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

    public boolean canAttack(Piece piece, Chessboard chessboard, Position position) {

        if (piece instanceof Pawn pawn) {
            return pawn.getAttackPattern().contains(position);
        }

        if (piece instanceof SlidingPiece slidingPiece) {
            return piece.getMovePattern().contains(position)
                    && hasNoPiecesOnPath(slidingPiece, chessboard, position);
        }

        return piece.getMovePattern().contains(position);
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
                    && hasNoPiecesOnPath(slidingPiece, chessboard, nextPosition)
                    && (isEnemy || isPositionEmpty);
        }


        return isInMovePattern(piece, nextPosition) && (isEnemy || isPositionEmpty);
    }

    public boolean checkKingSafeRules(Piece piece, Chessboard chessboard, Position nextPosition) {
        Chessboard simulatedChessboard = chessboard.copy();
        Piece currentSimulatedPiece = simulatedChessboard.getPieceFromPosition(piece.getPosition());
        Piece nextPiece = simulatedChessboard.getPieceFromPosition(nextPosition);

        if (nextPiece != null) {
            simulatedChessboard.remove(nextPiece);
        }

        currentSimulatedPiece.setPosition(nextPosition);


        return !isKingAttacked(simulatedChessboard, currentSimulatedPiece.getColor());
    }


    public boolean isCheckmate(Chessboard chessboard, Color color) {
        return isKingAttacked(chessboard, color) && hasNoLegalMoveForColor(chessboard, color);
    }

    public boolean isStalemate(Chessboard chessboard, Color color) {
        return !isKingAttacked(chessboard, color) && hasNoLegalMoveForColor(chessboard, color);
    }

    private boolean hasNoLegalMoveForColor(Chessboard chessboard, Color color) {
        for (Piece piece : chessboard.getPieces()) {
            if (color == piece.getColor()) {
                for (Position position : piece.getMovePattern()) {
                    if (checkMovement(piece, chessboard, position)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }


}
