package org.javachess.enums;

import org.javachess.entity.*;
import org.javachess.interfaces.PositionValidator;

public enum PromotingPieceOptions {
    QUEEN,
    ROOK,
    KNIGHT,
    BISHOP;

    public Piece create(Pawn pawn) {
        Position piecePosition = pawn.getPosition();
        PositionValidator piecePositionValidator = pawn.getPositionValidator();
        Color pieceColor = pawn.getColor();

        return switch (this) {
            case QUEEN -> new Queen(piecePosition, piecePositionValidator, pieceColor);
            case ROOK -> new Rook(piecePosition, piecePositionValidator, pieceColor);
            case KNIGHT -> new Knight(piecePosition, piecePositionValidator, pieceColor);
            case BISHOP -> new Bishop(piecePosition, piecePositionValidator, pieceColor);
        };
    }
}
